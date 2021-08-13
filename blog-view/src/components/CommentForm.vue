<template>
  <!-- 评论输入表单 -->
  <div class="form">
    <h3>
      发表评论
      <el-button v-show="this.realParentCommentId !== -1" class="m-small" size="mini" type="primary"
                 @click="toSendParentId()">取消回复
      </el-button>
    </h3>
    <el-form ref="formRef" :model="commentForm" :rules="rules" size="small">
      <el-input v-model="commentForm.content" :class="'textarea'" :rows="5" :validate-event="false" maxlength="250"
                placeholder="评论千万条，友善第一条" show-word-limit type="textarea"></el-input>

      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item prop="nickname">
            <el-popover ref="nicknamePopover" content="输入QQ号将自动拉取昵称和头像" placement="bottom" trigger="focus"></el-popover>
            <el-input v-model="commentForm.nickname" v-popover:nicknamePopover :validate-event="false" placeholder="昵称（必填）"
                      @blur="onInputBlur">
              <i slot="prefix" class="el-input__icon el-icon-user"></i>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="email">
            <el-popover ref="emailPopover" content="用于接收回复邮件" placement="bottom" trigger="focus"></el-popover>
            <el-input v-model="commentForm.email" v-popover:emailPopover :validate-event="false" placeholder="邮箱（必填）">
              <i slot="prefix" class="el-input__icon el-icon-message"></i>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="website">
            <el-popover ref="websitePopover" content="可以让我参观一下吗😊" placement="bottom" trigger="focus"></el-popover>
            <el-input v-model="commentForm.website" v-popover:websitePopover :validate-event="false"
                      placeholder="你的网站（可选）">
              <i slot="prefix" class="el-input__icon el-icon-map-location"></i>
            </el-input>
          </el-form-item>
        </el-col>

        <el-col :offset="1" :span="5">
          <el-form-item>
            <!-- 接口节流  -->
            <el-button size="medium" type="primary"  v-throttle="[postForm,`click`,30000]"  @mouseenter.native="beforePost">发表评论</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>


  </div>
</template>

<script>

export default {
  name: "CommentForm",
  props: {
    realParentCommentId: {
      type: Number,
      default: '-1'
    },
    realParentCommentNickname: {
      type: String,
      default: ""
    }

  },
  data() {
    var checkEmail = (rule, value, callback) => {
      // 验证邮箱的正则表达式
      const regEmail = /^\w+@\w+(\.\w+)+$/
      if (regEmail.test(value)) {
        return callback()
      }
      //返回一个错误提示
      callback(new Error('请输入合法的邮箱'))
    }

    return {

      commentForm: {
        content: "",
        nickname: "",
        email: "",
        website: "",
        avatar: "",
        blogId: 0,
        parentCommentId: this.realParentCommentId,
        isAdminComment: -1,
        qq: "",
        parentCommentNickname: this.realParentCommentNickname
      },
      rules: {
        nickname: [
          {required: true, message: '请输入评论昵称'},
          {max: 15, message: '昵称不可多于15个字符', trigger: 'blur'}
        ],
        email: [
          {required: true, message: '请输入评论邮箱'},
          {min: 2, max: 30, message: '不可多于30个字符、少于2个字符', trigger: 'blur'},
          {validator: checkEmail}
        ],
        website: [

          {max: 30, message: '不可多于30个字符'}
        ],

      }

    }
  },
  methods: {
    //设计哈希函数
    //1>将字符串转成比较大的数字：hashCode
    //2>将大的数字hashCode压缩到数组范围
    hashFunc(str, size) {
      //1.定义hashCode变量
      var hashCode = 0
      //2.霍纳算法，来计算 hashCode的值
      for (var i = 0; i < str.length; i++) {
        hashCode = 37 * hashCode + str.charCodeAt(i) //获取编码
      }
      //3.取余状态
      var index = hashCode % size + 1
      return index
    },
    //取消回复 向父组件传值 并设置评论父id
    toSendParentId() {
      this.$emit('parentEvent', '取消回复了')
      //console.log("取消")
      this.commentForm.parentCommentId = -1
    },
    //失去焦点 自动获取qq昵称和头像 或者非qq设置随机头像
    onInputBlur() {
      //判断是否是qq号码
      const _this = this
      var qq = this.commentForm.nickname
      if (!isNaN(Number(qq)) && qq.length > 4 && qq.length < 12) {
        this.$axios.get('https://api.usuuu.com/qq/' + qq).then(res => {
          if (res.data) {
            _this.commentForm.nickname = res.data.data.name
            _this.commentForm.avatar = res.data.data.avatar
            _this.commentForm.qq = res.data.data.qq
            //console.log(JSON.stringify(_this.commentForm))
            //console.log("哈哈哈哈哈")
          }
        })
      } else {
        //根据昵称随机头像
        var randomNum = this.hashFunc(this.commentForm.nickname, 20)
        this.commentForm.avatar = "https://cdn.jsdelivr.net/gh/yubifeng/blog-resource/bloghosting/2021/avatar/avatar" + randomNum + ".webp"
        //console.log(JSON.stringify(this.commentForm))
      }
    },
    //提交之前检查头像地址是否为空
    beforePost() {
      if (this.commentForm.avatar == "") {
        this.onInputBlur()
      }
      //console.log("sdaf ")

    },

    //提交评论
    postForm() {
      //console.log(JSON.stringify(this.commentForm))
      //表单校验
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          console.log(JSON.stringify(this.commentForm))
          //判断是否为管理员(博主)
          if (this.commentForm.isAdminComment == 1) {
            this.commentForm.avatar = "https://cdn.jsdelivr.net/gh/yubifeng/blog-resource/bloghosting//website/static/websiteAvatar.webp"
          }
          const _this = this
          this.$axios.post('/comment/add', this.commentForm).then(res => {
            //console.log(res)
            if (res.data.code == 200) {
              _this.$alert('发送成功', '提示', {
                confirmButtonText: '确定',
                callback: action => {
                  //_this.$router.push("/blog/")
                  location.reload()
                }
              });
            } else {
            }
          })
          // //alert('submit!');
          // //成功后清空表单
          // this.$refs.formRef.resetFields();
          // this.commentForm.content = ""
          // this.commentForm.qq = ""
          // this.commentForm.avatar = ""
        } else {
          console.log('error submit!!');
          alert('输入数据不合法，请检查');
          return false;
        }
      });
    }
  },
  watch: {
    //监控props 但是好像没用额， 因为为在父类中没改动
    realParentCommentId: function (newVal) {
      this.commentForm.parentCommentId = newVal
      console.log(JSON.stringify(this.commentForm))
    }
  },
  created() {
    if (this.$route.params.blogId) {
      this.commentForm.blogId = this.$route.params.blogId
    } else if (this.$route.path == "/about") {
      this.commentForm.blogId = 1
    } else if (this.$route.path == "/friends") {
      this.commentForm.blogId = 11
    } else {
      alert("error")
      return false
    }
    if (this.$store.getters.getUser) {
      if(this.$store.getters.getUser.role==="role_admin"||this.$store.getters.getUser.role==="role_root") {
        this.commentForm.isAdminComment = 1;
      }
    }
  },
}
</script>

<style scoped>
.form h3 {
  margin: 5px;
  font-weight: 500 !important;
}
.form .m-small {
  margin-left: 5px;
  padding: 4px 5px;
}
.el-form .textarea {
  margin-top: 5px;
  margin-bottom: 15px;
}
.el-form textarea {
  padding: 6px 8px;
}
.el-form textarea, .el-form input {
  color: black;
}
.el-form .el-form-item__label {
  padding-right: 3px;
}
</style>