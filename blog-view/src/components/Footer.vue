<template>
  <!--网站底部-->
  <div class="footer">
    <el-row :gutter="12" style="padding-top: 30px">
      <el-col :sm="3" style="font-size: 36px">
        <router-link to="/">
          <el-link style="font-size: 26px">Skymo</el-link>
        </router-link>
      </el-col>
      <el-col :offset="3" :sm="6" style="line-height: 40px">
        在变幻的生命里，时间才是最大的小偷
      </el-col>
      <el-col :offset="3" :sm="9">
        <el-input
            v-model="input"
            clearable
            placeholder="查找文章">
        </el-input>
      </el-col>
    </el-row>
    <el-divider></el-divider>
    <el-row :gutter="24">
      <el-col :sm="8">
        <el-row :gutter="24" class="footer-title">
          <el-col :span="24">座右铭</el-col>
        </el-row>
        <el-row :gutter="24" class="footer-content">
          <el-col :span="24">
            Nothing is impossible!!
          </el-col>
        </el-row>
      </el-col>
      <el-col :sm="8">
        <el-row :gutter="24" class="footer-title">
          <el-col :span="24">个人简介</el-col>
        </el-row>
        <el-row :gutter="24" class="footer-content">
          <el-col :span="24">
            博主比较懒，还没写。
          </el-col>
        </el-row>
      </el-col>
      <el-col :sm="8">
        <el-row :gutter="24" class="footer-title">
          <el-col :span="24">联系博主</el-col>
        </el-row>
        <el-row :gutter="24" class="footer-content">
          <el-col :sm="12">
            网站维护不易，如果觉得博客内容不错，愿意请博主喝杯咖啡，可以扫描旁边二维码。有问题也可以联系博主，QQ：894904864
          </el-col>
          <el-col :sm="12">
            <el-image :src="src" style="width: 80px; height: 80px"></el-image>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-divider></el-divider>
    <el-row :gutter="20" style="height: 5px;margin-top: 25px;font-size: 12px">
      <el-col :span="24">
        Copyright&nbsp;&copy;&nbsp;2021-2022&nbsp;Skymo's&nbsp;Blog.&nbsp;All&nbsp;rights&nbsp;reserved.&nbsp;
      </el-col>
    </el-row>
    <el-row :gutter="4" style="height: 30px;margin-top: 15px;font-size: 12px;">
      <el-col :span="8" :offset="5">
        {{'PV: '+pv}}
      </el-col>
      <el-col :span="2">
        {{'UV: '+uv}}
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Footer",
  data() {
    return {
      pv: 0,
      uv: 0,
      input: "",
      src: 'https://cdn.jsdelivr.net/gh/yubifeng/blog-resource/bloghosting//website/static/footPict.webp',

    }
  },
  methods:{
    //获取pv和uv
    getPVAndUV(){
      const _this = this
      this.$axios.get('/visitornum').then(res => {
        _this.uv = res.data.data.uv
        _this.pv = res.data.data.pv
      })

    }
  },
  // 监听,当路由发生变化的时候执行
  watch: {
    '$route':'getPVAndUV'
  },
  created() {
    this.getPVAndUV()
  }
}
</script>

<style scoped>
.footer-title {
  font-size: 13px;
  font-weight: bold;
  margin-bottom: 5px;
}

.footer-content {
  font-size: 12px;
}

.footer {
  margin: 0 auto;
  text-align: center;
  width: 60% !important;
  height: 20px;
}
</style>