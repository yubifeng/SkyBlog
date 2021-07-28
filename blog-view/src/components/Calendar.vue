<template>
  <!--日历-->
  <div class="blog-calendar">
    <el-calendar>
    </el-calendar>
  </div>
</template>

<script>
export default {
  name: "Calendar"
}
</script>

<style scoped>
.blog-calendar >>> .el-calendar div.el-calendar-day {
  height: 30px;
  font-size: 10px;


}
.blog-calendar >>> .el-calendar div.el-calendar__header {
  height: 40px;
  font-size: 10px;
  padding: 10px 10px 10px 10px;

}
.blog-calendar >>> .el-calendar div.el-calendar__body {
  padding: 10px 10px 10px 10px;
  font-size: 10px;
}
.blog-calendar {
  margin-top: 30px;
  width: 250px;

}
.blog-calendar >>> .el-calendar div.el-calendar__title {
  padding: 5px 5px 5px 5px;
}
.blog-calendar >>> .el-calendar button.el-button--plain.el-button--mini {
  padding: 8px 8px 8px 8px;
  max-height: 26px;

}
.blog-calendar >>> .el-calendar div.el-calendar__button-group {
  height: 30px;
  margin-top: 6px;
}
</style>