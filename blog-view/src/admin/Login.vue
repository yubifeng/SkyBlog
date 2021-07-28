<template>
  <div>
    <el-container>
      <!--头像-->
      <el-header>
        <router-link to="/admin">
          <img alt="" class="mlogo" src="https://cdn.jsdelivr.net/gh/yubifeng/blog-resource/bloghosting/website/static/websiteAvatar.webp">
        </router-link>
      </el-header>
      <!--主界面-->
      <el-main>
        <el-form ref="ruleForm" :model="ruleForm" :rules="rules" class="demo-ruleForm" label-width="100px" status-icon>
          <el-form-item label="用户名" prop="username">
            <el-input v-model="ruleForm.username" maxlength="12" type="text"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="ruleForm.password" autocomplete="off" type="password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        password: '',
        username: ''
      },
      rules: {
        password: [
          {validator: validatePass, trigger: 'blur'}
        ],
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    //登录
    submitForm(formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 提交逻辑
          this.$axios.post('/login', this.ruleForm).then((res) => {
            const token = res.headers['authorization']
            _this.$store.commit('SET_TOKEN', token)
            _this.$store.commit('SET_USERINFO', res.data.data)
            _this.$router.push("/admin")
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    //清空用户名密码
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  mounted() {
  }
}
</script>

<style scoped>
.el-header, .el-footer {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 60px;
}
.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}
.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 160px;
}
body > .el-container {
  margin-bottom: 40px;
}
.mlogo {
  height: 60%;
  margin-top: 10px;
}
.demo-ruleForm {
  max-width: 500px;
  margin: 0 auto;
}
</style>