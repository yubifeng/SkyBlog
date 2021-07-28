<template>
  <div class="card">
    <!--展示名片头部--头像名称-->
    <div class="card-header">
      <el-avatar :size="50" :src="user.avatar"></el-avatar>
      <p style="margin-top: 0px;">Skymo</p>
      <el-divider></el-divider>
    </div>
    <!--卡片中部--github地址-->
    <div class="card-middle">
      <div style="max-width: 40px;margin-left: 40px">
        <el-image :fit="fit"
                  src="https://cdn.jsdelivr.net/gh/yubifeng/blog-resource/bloghosting//website/static/githubLogo.webp"
                  style="cursor:pointer" @click="goToGithub"></el-image>
      </div>
    </div>
    <!--卡片底部--个人喜好-->
    <div class="card-footer">
      <el-collapse>
        <el-collapse-item name="1" style="padding-left: 20px;padding-right: 20px" title="最喜欢的动漫">
          <div>轻音少女、辉夜大小姐想让我告白、青春猪头少年不会梦到兔女郎学姐、路人女主、辉夜大小姐想让我告白、超炮、俺妹</div>
        </el-collapse-item>
        <el-collapse-item name="2" style="padding-left: 20px;padding-right: 20px" title="最喜欢的女孩子">
          <div>樱岛麻衣、亚丝娜、高坂桐乃、五更琉璃、英梨梨、珈百璃、、和泉纱雾</div>
        </el-collapse-item>
        <el-collapse-item name="3" style="padding-left: 20px;padding-right: 20px" title="最喜欢的游戏">
          <div>暂无</div>
        </el-collapse-item>
        <el-collapse-item name="4" style="padding-left: 20px;padding-right: 20px" title="最喜欢的颜色">
          <div>蓝色、紫色</div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
export default {
  name: "Card",
  data() {
    return {
      hasLogin: false,
      fit: 'fill',
      user: {
        avatar: "https://cdn.jsdelivr.net/gh/yubifeng/blog-resource/bloghosting//website/static/websiteAvatar.webp"
      },
    }
  },
  methods: {
    //跳转到个人github
    goToGithub() {
      window.location.href = "https://github.com/yubifeng"
    }
  },
  created() {
  }
}
</script>

<style scoped>
.card {
  width: 250px;
  padding: 20px 0px 0px 0px;
  background-color: white;
}
.card-header {
  text-align: center;
}
.card-footer {
  margin-top: 10px;
}
.card:hover {
  -webkit-box-shadow: #ccc 0px 10px 10px;
  -moz-box-shadow: #ccc 0px 10px 10px;
  box-shadow: #ccc 0px 10px 10px;
}
</style>