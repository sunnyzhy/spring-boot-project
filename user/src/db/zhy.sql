-- phpMyAdmin SQL Dump
-- version 4.8.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: 2018-06-06 06:52:06
-- 服务器版本： 5.7.22-log
-- PHP Version: 7.2.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `zhy`
--

-- --------------------------------------------------------

--
-- 表的结构 `t_role`
--

DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL COMMENT '角色id，主键，自增',
  `name` varchar(32) NOT NULL COMMENT '角色名称',
  `description` varchar(64) DEFAULT NULL COMMENT '角色描述'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `t_role_user`
--

DROP TABLE IF EXISTS `t_role_user`;
CREATE TABLE `t_role_user` (
  `id` int(11) NOT NULL COMMENT '角色-用户id，主键，自增',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `user_id` int(11) NOT NULL COMMENT '用户id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `t_user`
--

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL COMMENT '用户id，主键，自增',
  `name` varchar(32) NOT NULL COMMENT '姓名',
  `user_name` varchar(32) NOT NULL COMMENT '登录的用户名',
  `password` varchar(64) NOT NULL COMMENT '登录的密码',
  `sex` char(1) NOT NULL DEFAULT '男' COMMENT '性别',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户状态 0:注销 1:正常',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户类型 0:普通用户 1:管理员'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `t_role`
--
ALTER TABLE `t_role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_role_user`
--
ALTER TABLE `t_role_user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `t_user`
--
ALTER TABLE `t_user`
  ADD PRIMARY KEY (`id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `t_role`
--
ALTER TABLE `t_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id，主键，自增';

--
-- 使用表AUTO_INCREMENT `t_role_user`
--
ALTER TABLE `t_role_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色-用户id，主键，自增';

--
-- 使用表AUTO_INCREMENT `t_user`
--
ALTER TABLE `t_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id，主键，自增';
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
