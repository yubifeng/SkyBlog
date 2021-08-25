<template>
  <div class="head-photo" style="width: 100%;height: 800px">
    <!-- banner -->

    <img src="https://cdn.jsdelivr.net/gh/yubifeng/blog-resource/bloghosting/2021/04/27/340911.jpg" style="width: 100%;height: 100%">


    <div class="banner-container">
    <el-row style="height: 95vh">
      <el-col :span="24" style="height: 100%">
        <el-card shadow="none" class="welcome">
          <h1 class="tit">
            欢迎来到Skymoの个人博客
            <div class="border"></div>
          </h1>
          <h2 class="intro">{{intro}}
          </h2>


          <div id="waypoint" class="mini-nav" align="center">
            <router-link  class="item el-icon-s-home" to="/">
              首页
            </router-link>
            <router-link  class="item el-icon-time" to="/archives">
              时间轴
            </router-link>

            <router-link  class="item el-icon-edit" to="/friends">
              友人账
            </router-link>
            <router-link class="item el-icon-info" to="/about">
              关于我
            </router-link>
          </div>

          <!-- 向下滚动 -->
          <div class="bounce down"  @click="startRead"  >
            <i class="el-icon-arrow-down" style="color: white"></i>
          </div>

        </el-card>
      </el-col>
    </el-row>
    </div>
  </div>
</template>

<script>

export default {
  name: "HeadPhoto",
  data() {
    return {
      intro: '',
      intersectionOptions: {
        root: document.querySelector("#app"), //用作视口的元素，用于检查目标的可见性。必须是目标的祖先。如果未指定，则默认为浏览器视口null
        rootMargin: "0px",
        threshold: 0.33 //范围为 0-1: 阈值为1.0意味着当100％的目标在root选项指定的元素中可见时，将调用回调
      }
    }
  },
  methods:{
    // 开始进入主页
    startRead() {
      this.$nextTick(() => {
        this.$emit('fatherMethod')
      })
    },
  },
  mounted() {
    let str = '这是我的个人博客、会分享关于编程、开发的一些内容，以后也会写一些随笔，希望可以对您有所帮助...';
    let idx = 0;
    let that = this
    let timer = setTimeout( function fn() {
      // console.log(this.intro)
      that.intro = that.intro+ str.substring(idx,idx+1)
      idx++
      if (idx>str.length){
        that.intro = ''
        idx = 0
      }
      setTimeout(fn,200)
    },2000)

    // 导航栏显示
    var waypoint = new Waypoint({
      element: document.getElementById('waypoint'),
      handler: function (direction) {
        if(document.getElementById('waypoint')!==null){
          if(document.getElementById('header-photo').style.display!=='none'){
            if (direction === 'down') {
              $('#nav').show(500);
            } else {
              $('#nav').hide(500);
            }
            // console.log('Scrolled to waypoint!  ' + direction);

          }

        }
      }
    });

  }

}
</script>

<style scoped>
.head-photo {
  margin-bottom: 20px;
  text-align: center;
  position: relative;
  top: 0;

}
.photo {
  text-align: center;
  color: #fff !important;
  height: 100vh;


}
.head-photo img {


}

.banner-container {

  line-height: 1.5;
  color: #eee;
  position:absolute;
  top: 10%;
  left: 5%;
  width: 90%;
  height: 100%;


}
.photo {

  width: 100%;

}
.photo img {
  width: 100%;
  height: 100%;
}

.intro {
  letter-spacing: 5px;
  line-height: 50px;
  width: 80%;
  margin: 0 auto;
  text-align: center;
  font-weight: normal;
  color: white;
}

.down {
  animation: bounce 2s infinite;
  animation-duration: 3s;
  font-size: 25px;
  position: absolute;
  bottom: 5px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  justify-content: center;
  align-items: center;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: 2px solid #fff;
}

.down:hover {
  animation: none;
  cursor: pointer;
  box-shadow: 0 0 20px 0 white;
  transition: all .2s;
}
@keyframes bounce {
  0%, 20%, 50%, 80%, 100% {
    transform: translate(-50%, 0);
  }
  40% {
    transform: translate(-50%, -30px);
  }
  60% {
    transform: translate(-50%, -15px);
  }
}
@keyframes clipMe {
  0%,
  100% {
    clip: rect(0px, 806px, 6px, 0px);
  }

  25% {
    clip: rect(0px, 6px, 112px, 0px);
  }

  50% {
    clip: rect(112px, 812px, 112px, 0px);
  }

  75% {
    clip: rect(0px, 812px, 112px, 806px);
  }
}
.welcome {
  background-color: rgba(0, 0, 0, 0.1);
  /*background: transparent!important;*/
  border: none;
  height: 90%;
  position: relative;
}

.border {
  width: 812px;
  height: 112px;
  position: absolute;
  top: -6px;
  left: -6px;
  border: 3px solid white;
  box-sizing: border-box;
  animation: clipMe 5s linear infinite;
}

.tit {
  box-sizing: border-box;
  position: relative;
  width: 800px;
  height: 100px;
  line-height: 100px;
  box-shadow: inset 0 0 0 1px white;
  margin: 40px auto;
  margin-top: 80px;
  color: white;
  text-align: center;
  font-size: 50px;
  font-weight: normal;
  letter-spacing: 10px;
}
.mini-nav {
  position: absolute;
  top: 70%;
  left: 36%;
}
.item {
  border-radius: 10em;
  font-family: Lato,'Helvetica Neue',Arial,Helvetica,sans-serif;
  background: #E0E1E2;
  margin: 0 15px 0 0;
  line-height: 1em;
  text-align: center!important;
  font-style: normal;
  text-decoration: none;

  transition: opacity .1s ease,background-color .1s ease,color .1s ease,box-shadow .1s ease,background .1s ease;
  opacity: 0.4 !important;
  color: rgba(0,0,0,.6);
  padding: 11px 12px 11px 12px;
  min-height: 1em;
  vertical-align: baseline;

  outline: 0;


}
</style>