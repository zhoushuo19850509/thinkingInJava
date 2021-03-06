这个package下的代码，说明了死锁的的场景(DeadlockingDiningPhilosopher.java)，
以及如何解决死锁(FixedDiningPhilosopher.java)。

基于这个思想，我们可以应用到工作实际中去：
 * 这个解决死锁的场景，给我们提供了一个很好的思路

 * 我们在项目中，也会经常碰到死锁的场景
 * 比如我们解决了很久了拷贝服务问题。本质上就是两个长事务造成的死锁。
 * 场景也很好重现：
 * 事务A，扫描完成后立即触发上传，要做的事情是：
 * 0.保存索引信息(biz/doc/file/传输表)
 * 1.更新传输表状态；
 * 2.(起一个单独的线程)触发文件上传；
 * 3.触发上传成功后(子线程)回写数据(更新file表、删除传输表)
 *
 * 事务B，跨网拷贝，要做的事情：
 * 1.(同步)触发上传；
 *   修改传输表、同步触发上传、回写索引数据(更新file表、删除传输表)
 * 2.(同步)触发NAS上传；
 *   修改trans_nas表、触发上传到NAS、回写索引数据(更新file表、删除trans_nas表)
 *
 * 我们通过日志发现，事务B经常会发生死锁(超时)
 * 发生死锁的地方包括：
 * 回写索引数据(更新file表cmfileId字段）
 * 1.(同步)触发上传；
 * 2.(同步)触发NAS上传；
 * 回写索引数据(更新file表nasId字段)
 *
 * 为什么会发生死锁呢？
 * 总体来说，是一个死锁的环：
 * 事务B触发上传完成后，回写索引数据更新file表cmfileId字段的时候，file表这条记录正在被事务A占用着。因为事务A触发上传结束后，先更新file表cmfileId
 * 事务A更新完file表cmfileId，要开始删除传输表的时候，发现传输表被事务B锁着，因为事务B一上来就是更新传输表状态。
 * 这样，就造成了事务A和事务B死锁的场景。
 *
 * 后续：
 * 1.为了更加形象说明死锁，建议参考之前考数据库工程师时候的参考书，那里有死锁相关的图例和解决方案
 * 2.深入了解一下Spring事务控制、数据库事务控制的原理，至少要搞清楚在死锁超时异常抛出来的时候，是谁在占用数据库的行级锁
 *
 如何解决死锁呢？
 1.尽量减小事务的粒度
 2.