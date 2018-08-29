CREATE TABLE `ffmpeg_task1` (
  `id` varchar(50) NOT NULL,
  `time` int(11) DEFAULT NULL , -- 任务插入时间
  `status` int(1) DEFAULT '0', -- 任务状态0未执行1执行中2完成
  `start_time` int(11) DEFAULT '0' , -- 任务开始执行时间
  `end_time` int(11) DEFAULT '0' , -- 任务执行结束时间
  `inputPath` varchar(255) DEFAULT NULL , -- 输入文件路径
  `outputPath` varchar(255) DEFAULT NULL , -- 输出文件路径
  `cmd` varchar(1024) DEFAULT NULL , -- 命令
  `descript` varchar(1024) DEFAULT NULL , -- 描述
  PRIMARY KEY (`id`)
);
CREATE TABLE `label_info` (
  `id` varchar(50) NOT NULL,
  `label_name` varchar(255) NOT NULL,
  `describe` varchar(512) DEFAULT NULL,
  `last_change_time` int(11) DEFAULT NULL,
  `label_class` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
CREATE TABLE `playactor_info` (
  `id` varchar(50) NOT NULL,
  `name` varchar(255) DEFAULT NULL ,
  `img` longblob  ,
  `type` varchar(255) DEFAULT NULL , -- 类型
  `point` varchar(255)  DEFAULT NULL , -- 特点
  `face_rate` varchar(255)  DEFAULT NULL , -- 相貌评分
  `figure_rate` varchar(255) DEFAULT NULL , -- 身材评分
  `vagina_rate` varchar(255) DEFAULT NULL , -- 下体评分
  `breast_rate` varchar(255) DEFAULT NULL , -- 胸型评分
  PRIMARY KEY (`id`)
);
CREATE TABLE `store_video` (
  `id` varchar(50) NOT NULL,
  `designation` varchar(100) DEFAULT NULL , -- 番号
  `thumbnail` longblob , -- 缩略图
  `file_path` varchar(512) DEFAULT NULL , -- 文件存储路径（不含盘符）
  `file_path_disk` varchar(50) DEFAULT NULL, -- 存储硬盘sn
  `file_size` bigint(20) DEFAULT NULL, -- 文件大小
  `duration` varchar(255) DEFAULT NULL, -- 时长
  `resolution` varchar(255) DEFAULT NULL, -- 分辨率
  `bit_rate` varchar(255) DEFAULT NULL, -- 码率
  `fps` varchar(255) DEFAULT NULL, -- 帧率
  `mosaic` int(1) NOT NULL, -- 是否马赛克0否1是
  `subtitle` int(1) NOT NULL, -- 是否字幕0否1是
  `plot` int(1) NOT NULL, -- 是否剧情0否1是
  `type_id` varchar(50) DEFAULT NULL , -- 类型id
  `distributor_id` varchar(50) DEFAULT NULL , -- 片商id
  `location_id` varchar(50) DEFAULT NULL, -- 地区id
  `playactor_ids` varchar(512) DEFAULT NULL , -- 主演id，逗号拆分
  `failarmy` int(1) NOT NULL, -- 是否合集
  `scene` int(1) NOT NULL, -- 是否多场景0否1是
  `Creampie` int(1) NOT NULL, -- 是否内射0否1是
  `Facia` int(1) NOT NULL, -- 是否颜射0否1是
  `mouth_ejaculation` int(1), -- 是否口爆0否1是
  `eat_semen` int(1) NOT NULL, -- 是否吞精0否1是
  `more_man` int(1) NOT NULL, -- 是否多男0否1是
  `more_woman` int(1) NOT NULL, -- 是否多女0否1是
  `score` varchar(255) DEFAULT NULL , -- 评分
  `describe` varchar(512) DEFAULT NULL , -- 描述
  PRIMARY KEY (`id`)
);