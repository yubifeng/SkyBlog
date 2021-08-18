<template>
  <div class="index-container">
    <!--顶部导航栏-->
    <Navbar class = "navbar"  id="nav" v-show="$route.name!=='Home'||this.focusMode"></Navbar>
    <!--顶部图片-->
    <HeadPhoto  id="header-photo" :class="{'m-display-none':focusMode}" class="head-photo" v-if="$route.name==='Home'" @fatherMethod="fatherMethod"></HeadPhoto>

    <!--使用弹性盒子实现左中右结构-->
    <div class="index-main" id="index" >

      <!--左侧-->
      <div :class="{'m-display-none':focusMode}" class="index-main-column-left">
        <Card></Card>
      </div>


      <!--中间-->
      <div class="index-main-column-middle">
        <keep-alive include="Home">
          <router-view/>
        </keep-alive>
      </div>


      <!--右侧-->
      <div :class="{'m-display-none':focusMode}" class="index-main-column-right">

        <RightCard></RightCard>

        <Calendar></Calendar>
      </div>

    </div>

    <!--回到顶部-->
    <el-backtop style="box-shadow: none;background: none;">
      <img alt="" src="../assets/toTopLogo.webp" style="width: 40px;height: 40px;">
    </el-backtop>
    <!--底部footer-->
    <Footer class="index-foot"></Footer>





    <!-- 音乐播放器 -->
    <div>
      <meting-js server="netease" type="playlist" id="6859965088" fixed="true" mini="true" ></meting-js>
    </div>


    <div class="snow-container"></div>




  </div>
</template>




<script>

import Navbar from "../components/Navbar";
import Card from "../components/Card";
import Footer from "../components/Footer";
import RightCard from "@/components/RightCard";
import Calendar from "@/components/Calendar";
import HeadPhoto from "@/components/HeadPhoto";


export default {
  name: "Index",
  components: {RightCard, Footer, Navbar, Card, Calendar,HeadPhoto},
  data() {
    return {
      focusMode: false,
    }
  },
  watch: {
    //路由改变时，页面滚动至顶部
    '$route.path'() {
      this.scrollToTop()
      $('#nav').show(500);
    }
  },
  methods: {
    //向下滚动到"index"
    fatherMethod() {
      document.getElementById("index").scrollIntoView({
        behavior: 'smooth',
        block: 'start',
        // inline: 'nearest'
      });
    },
    //检查是否为手机端
    getIsPhone() {
      let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
      return flag;
    },

    //鼠标点击特效
    specialEffect(){
      let a_idx = 0;
      jQuery(document).ready(function ($) {
        $("body").click(function (e) {
          // var a = new Array("❤富强❤", "❤民主❤", "❤文明❤", "❤和谐❤", "❤自由❤", "❤平等❤", "❤公正❤", "❤法治❤", "❤爱国❤", "❤敬业❤", "❤诚信❤", "❤友善❤");
          var a = new Array("❤Java❤", "❤C++❤", "❤PHP❤", "❤Mysql❤", "❤Oracle❤", "❤.NET❤", "❤JavaScript❤", "❤H5❤", "❤Python❤", "❤Json❤", "❤Ruby❤", "❤Go❤");
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
            "color": "rgb(" + ~~(255 * Math.random()) + "," + ~~(255 * Math.random()) + "," + ~~(255 * Math.random()) + ")"
          });
          $("body").append($i);
          $i.animate({
                "top": y - 180,
                "opacity": 0
              },
              1500,
              function () {
                $i.remove();
              });
        });
      });
    },

    snowSpecial() {
      (function($){
        $.fn.snow = function(options){
          var $flake = $('<div id="snowbox" />').css({'position': 'absolute','z-index':'9999', 'top': '-50px'}).html('&#10052;'),
              documentHeight 	= $(document).height(),
              documentWidth	= $(document).width(),
              defaults = {
                minSize		: 10,
                maxSize		: 20,
                newOn		: 1000,
                flakeColor	: "#FFFFFF" /* 此处可以定义雪花颜色，若要白色可以改为#FFFFFF */
              },
              options	= $.extend({}, defaults, options);
          var interval= setInterval( function(){
            var startPositionLeft = Math.random() * documentWidth - 100,
                startOpacity = 0.5 + Math.random(),
                sizeFlake = options.minSize + Math.random() * options.maxSize,
                endPositionTop = documentHeight - 200,
                endPositionLeft = startPositionLeft - 500 + Math.random() * 500,
                durationFall = documentHeight * 10 + Math.random() * 5000;
            $flake.clone().appendTo('body').css({
              left: startPositionLeft,
              opacity: startOpacity,
              'font-size': sizeFlake,
              color: options.flakeColor
            }).animate({
              top: endPositionTop,
              left: endPositionLeft,
              opacity: 0.2
            },durationFall,'linear',function(){
              $(this).remove()
            });
          }, options.newOn);
        };
      })(jQuery);
      $(function(){
        $.fn.snow({
          minSize: 5, /* 定义雪花最小尺寸 */
          maxSize: 30,/* 定义雪花最大尺寸 */
          newOn: 400  /* 定义密集程度，数字越小越密集 */
        });
      });
    }
  },
  computed: {},
  mounted() {
    if (this.getIsPhone()) {
      this.focusMode = true

    }
    else {
      this.specialEffect()
      this.snowSpecial()
    }

  },

}


</script>


<style scoped>


.index-container {
  min-height: 100vh;
  background-image: url("https://cdn.jsdelivr.net/gh/yubifeng/blog-resource/bloghosting/2021/04/23/first87.png")!important; ;
  background-repeat: repeat-x;
  background-attachment:fixed;
  background-size:cover;
}

.navbar {
  position: fixed;
  top: 0;
  z-index:9999;
  width: 100%;
  opacity: 0.9;
}
.head-photo {

}


.index-foot {
  background-color: #333333;
  color: white!important;
  opacity: 0.9;

}

.index-main {
  display: flex;
  margin-top: 70px;
  margin-bottom: 40px;
  padding-top: 20px;
  margin-left: auto !important;
  margin-right: auto !important;

  opacity: 0.97;

}

.index-main-column-middle {
  flex: 1;
  width: 1400px !important;
  min-height: 700px;

}

.index-main-column-left {

  padding: 0;
  margin-right: 20px;
  margin-left: 30px;
}

.index-main-column-right {

  padding: 0;
  margin-left: 20px;
  margin-right: 30px;
}

.m-display-none {
  display: none !important;
}


</style>