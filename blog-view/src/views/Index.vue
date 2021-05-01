<template >
  <div class="index-container">

    <!--顶部导航栏-->
    <Navbar ></Navbar>


    <!--使用弹性盒子实现左中右结构-->
    <div class="index-main">

      <!--左侧-->
      <div class="index-main-column-left" :class="{'m-display-none':focusMode}" >
        <Card ></Card>
      </div>


      <!--中间-->
      <div class="index-main-column-middle"  >
        <keep-alive include="Home">
          <router-view/>
        </keep-alive>
      </div>



      <!--右侧-->
      <div class="index-main-column-right"   :class="{'m-display-none':focusMode}">

        <RightCard ></RightCard>

        <Calendar  ></Calendar>
      </div>

    </div>

    <!--回到顶部-->
    <el-backtop style="box-shadow: none;background: none;" >
      <img src="../assets/toTopLogo.webp" style="width: 40px;height: 40px;" alt="">
    </el-backtop>


    <!--底部footer-->
    <Footer ></Footer>





    <!-- 音乐播放器 -->
    <div>
      <meting-js server="netease" type="playlist" id="2288127006" mini="true" fixed="true"></meting-js>
    </div>

  </div>
</template>




<script>

import Navbar from "../components/Navbar";
import Card from "../components/Card";
import Footer from "../components/Footer";
import RightCard from "@/components/RightCard";
import Calendar from "@/components/Calendar";
import {mapState} from 'vuex'
export default {
  name: "Index",
  components: {RightCard, Footer, Navbar, Card, Calendar},
  data() {
    return {
      focusMode: false,


    }


  },
  watch: {
    //路由改变时，页面滚动至顶部
    '$route.path'() {
      this.scrollToTop()
    }
  },
  methods: {
    getIsPhone() {

      let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
      return flag;
    },




  },
  computed: {},
  mounted() {
    if (this.getIsPhone()) {
      this.focusMode = true
    }
    var a_idx = 0;

    jQuery(document).ready(function($) {

      $("body").click(function(e) {

        var a = new Array("❤富强❤","❤民主❤","❤文明❤","❤和谐❤","❤自由❤","❤平等❤","❤公正❤","❤法治❤","❤爱国❤","❤敬业❤","❤诚信❤","❤友善❤");

        var $i = $("<span></span>").text(a[a_idx]);

        a_idx = (a_idx + 1) % a.length;

        var x = e.pageX,

            y = e.pageY;

        $i.css({

          "z-index": 999999999999999999999999999999999999999999999999999999999999999999999,

          "top": y - 20,

          "left": x,

          "position": "absolute",

          "font-weight": "bold",

          "color": "rgb("+~~(255*Math.random())+","+~~(255*Math.random())+","+~~(255*Math.random())+")"

        });

        $("body").append($i);

        $i.animate({

              "top": y - 180,

              "opacity": 0

            },

            1500,

            function() {

              $i.remove();

            });

      });

    });


  }
}


</script>












<style scoped>
.index-container {
  min-height: 100vh;

}
.index-main{
  display: flex;
  margin-bottom: 20px;



  margin-left: auto !important;
  margin-right: auto !important;

}
.index-main-column-middle {
  flex: 1;
  width: 1400px !important;
  min-height: 700px;

}
.index-main-column-left{

  padding: 0;
  margin-right: 20px;
  margin-left: 30px;
}
.index-main-column-right{

  padding: 0;
  margin-left: 20px;
  margin-right: 30px;
}
.m-display-none {
  display: none !important;
}

</style>