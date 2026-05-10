项目名称：​ 智能家居控制系统（Smart Home Control System）
项目周期：​ 2026年
角色：​ 独立开发者
技术栈：​ Java SE、Swing GUI、面向对象编程、Java Serialization、设计模式
项目背景：​
为深入理解Java面向对象编程思想和设计模式，自主设计并开发了一款智能家居设备管理系统。该项目模拟真实智能家居场景，实现对多种家用电器的集中控制和管理，后端业务逻辑完全独立开发，前端界面借助AI辅助完成UI设计与实现。
主要功能：​
1.设备管理：​ 支持电视、LED灯、洗衣机、空调等4种智能设备的统一管理。
2.状态控制：​ 实时显示设备开关状态，支持一键切换设备工作状态。
3.双模交互：​ 提供图形界面（GUI）和命令行（Console）两种操作方式。
4.数据持久化：​ 基于Java序列化技术自动保存设备状态，程序重启后自动恢复。
5.用户友好：​ 采用Material Design设计规范，界面简洁美观，操作直观。


技术实现：​
1.架构设计
–采用前后端分离架构，后端业务逻辑层（jiadian基类、smartname控制器）与前端表现层（SmartHomeGUI）完全解耦。
–遵循MVC设计思想，Model层负责数据管理，View层负责界面展示，Controller层负责业务逻辑控制。
–后端核心代码独立完成，前端GUI界面在AI辅助下完成布局设计和样式优化。
 

2.后端核心开发（独立完成）​
–设计模式应用：​
•单例模式：​ smartname控制器类采用饿汉式单例实现，确保全局唯一的控制中心，通过getInstance()方法获取实例。
•接口抽象：​ 定义Switch接口规范设备行为，所有设备类实现press()方法，体现面向接口编程思想。
•继承体系：​ 构建以jiadian为基类的设备继承树，tv、lamp、washma、air四个子类继承通用属性和方法。
 

–面向对象特性实践：​
•封装：​ 设备名称（name）和状态（status）属性私有化，通过public的getName()、isStatus()、setName()、setStatus()方法访问，保证数据安全。
•继承：​ 四种设备类继承自jiadian基类，复用name、status属性和press()、getName()等公共方法，减少代码冗余。
•多态：​ 通过jiadian父类引用统一管理不同设备对象，调用press()方法时实现运行时动态绑定，体现多态特性。
 

–数据持久化实现：​
•jiadian基类实现Serializable接口，添加serialVersionUID确保版本兼容性。
•独立开发DataManager工具类，封装ObjectOutputStream和ObjectInputStream的读写操作。
•saveData()方法将设备数组序列化保存到smart_home_data.dat文件。
•loadData()方法从文件反序列化加载设备状态，文件不存在时返回null使用默认配置。
•完善的异常处理机制，捕获IOException和ClassNotFoundException。
 

–控制台版本开发：​
•基于Scanner类实现命令行输入读取。
•使用switch-case结构根据用户输入（1-4）选择对应设备进行控制。
•while(true)循环持续等待用户指令，支持“exit”命令优雅退出程序。
•退出前调用DataManager.saveData()保存当前设备状态。
 

 

3.前端GUI开发（AI辅助完成）​
–Swing框架应用：​
•使用JFrame作为主窗口容器，设置标题、尺寸和关闭行为。
•采用BorderLayout进行整体布局划分（NORTH标题区、CENTER设备区、SOUTH按钮区）。
•设备列表使用GridLayout实现4行1网格布局，保证设备卡片均匀分布。
•底部操作按钮使用FlowLayout居中排列。
 

–UI组件开发：​
•自定义createDeviceRow()方法创建设备行面板，包含图标、名称、状态标签和控制按钮。
•实现createModernButton()方法生成扁平化风格按钮，设置字体、颜色和边框样式。
•状态标签（JLabel）与控制按钮文字保持同步，开启/关闭状态使用不同颜色区分（绿色#34A853表示开启，红色#EA4335表示关闭）。
 

–事件驱动编程：​
•为每个控制按钮注册ActionListener，点击时调用controller.control()执行业务逻辑。
•实现updateStatusDisplay()方法遍历设备数组，根据isStatus()更新UI显示。
•窗口关闭事件通过WindowAdapter监听，关闭前自动调用DataManager.saveData()保存数据。
•退出按钮添加确认对话框，用户确认后保存数据并调用System.exit(0)退出。
 

–样式设计：​
•色彩方案遵循Google Material Design规范：主色蓝色#4285F4、成功色绿色#34A853、警告色红色#EA4335。
•背景色使用浅灰色#FAFAFA，卡片背景为纯白色，边框使用淡灰色#DADCE0。
•文字颜色采用深灰#202124（主要文字）和浅灰#5F6368（次要文字）。
•按钮悬停效果通过MouseListener实现，鼠标进入时显示浅色背景。
 

 

4.前后端集成
–GUI层通过smartname.getInstance()获取单例控制器，调用control()方法控制设备。
–设备状态变化后，GUI层调用updateStatusDisplay()刷新界面显示。
–DataManager工具类被GUI和控制台两个版本共用，实现数据持久化逻辑复用。
–后端业务逻辑（设备控制、状态管理）与前端界面（UI展示、事件处理）完全分离，互不影响。
 



项目难点与解决：​
•问题1：匿名内部类中this指向错误
–现象：​ 在ActionListener匿名类中使用this传递给JOptionPane.showMessageDialog()时编译报错。
–原因：​ 匿名内部类中的this指向监听器对象本身，而非外部JFrame实例。
–解决：​ 使用SmartHomeGUI.this明确指定外部类实例，正确传递窗口引用。
 

•问题2：JDK版本与预览功能兼容性
–现象：​ IDEA配置Java 25并启用预览功能时报错“源发行版25与--enable-preview一起使用时无效”。
–原因：​ 预览语言功能仅支持下一版本（Java 26），当前JDK不支持。
–解决：​ 调整IDEA项目配置，File → Project Structure中将Language level改为“25 - No preview features”或使用稳定LTS版本（17/21）。
 

•问题3：状态同步显示
–现象：​ 点击按钮控制设备后，界面状态标签未及时更新。
–原因：​ 缺少UI刷新机制，状态变化后未触发重绘。
–解决：​ 建立updateStatusDisplay()方法，遍历设备数组根据isStatus()更新标签文字和颜色，调用mainPanel.repaint()强制刷新界面。
 



项目收获：​
•深入理解Java面向对象三大特性（封装、继承、多态）在实际项目中的应用场景。
•掌握单例模式、接口抽象、继承体系等设计模式的实现方法和适用场景。
•熟悉Swing GUI开发流程，包括布局管理器、事件监听、组件自定义等核心技术。
•学会Java序列化技术和文件I/O操作，实现对象状态的持久化存储。
•培养前后端分离的架构思维，理解业务逻辑层与表现层的职责划分。
•提升问题分析能力和Debug技巧，能够独立解决编译错误和运行时异常。
•体验AI辅助开发的效率提升，学习如何有效利用AI工具完成UI设计和代码生成。


代码规模：​ 约500行Java代码，包含9个类文件（Switch接口、jiadian基类、4个设备子类、smartname控制器、DataManager工具类、test控制台入口、SmartHomeGUI图形界面入口）。
项目亮点：​
•后端核心业务逻辑完全独立开发，体现扎实的Java基础功底。
•完整运用面向对象思想和设计模式，代码结构清晰、可扩展性强。
•实现数据持久化功能，保证用户体验的连续性。
•提供GUI和Console双版本，满足不同使用场景需求。
•前后端分离架构，便于功能扩展和维护升级。
