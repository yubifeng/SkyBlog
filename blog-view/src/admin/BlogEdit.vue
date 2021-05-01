<template>
  <div>


    <div class="m-content">

      <el-form ref="ruleForm" :model="ruleForm" :rules="rules" class="demo-ruleForm" label-position="top"
               label-width="100px">


        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="文章标题" prop="title">
              <el-input v-model="ruleForm.title" placeholder="请输入标题"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="文章首图URL" prop="firstPicture">
              <el-input v-model="ruleForm.firstPicture" placeholder="文章首图，可为空"></el-input>
            </el-form-item>
          </el-col>
        </el-row>


        <el-form-item label="文章描述" prop="description">
          <mavon-editor v-model="ruleForm.description"/>
        </el-form-item>

        <el-form-item label="文章正文" prop="content">
          <mavon-editor v-model="ruleForm.content"></mavon-editor>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="分类" prop="cate">
              <el-select v-model="ruleForm.typeId" :allow-create="true" :filterable="true" placeholder="请选择分类（输入可添加新分类）"
                         style="width: 100%;">
                <el-option v-for="type in types" :key="type.id" :label="type.typeName" :value="type.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标签" prop="tagList">
              <el-select v-model="ruleForm.tagList" :allow-create="true" :filterable="true"
                         :multiple="true" placeholder="请选择标签（输入可添加新标签）" style="width: 100%;">
                <el-option v-for="item in tagList" :key="item.id" :label="item.name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="字数" prop="words">
              <!-- 双击计算文章字数-->
              <el-input v-model="ruleForm.words" placeholder="请输入文章字数" type="number"
                        @dblclick.native="computeWords"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item label="浏览次数" prop="views">
              <el-input v-model="ruleForm.views" placeholder="请输入文章字数（可选）默认为 0" type="number"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否公开" prop="publised">
              <el-tooltip :content="'Switch value: ' + ruleForm.status" placement="top">
                <el-switch
                    v-model="ruleForm.status"

                    :active-value="0"
                    :inactive-value="1">
                </el-switch>
              </el-tooltip>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>

    </div>


  </div>
</template>

<script>

export default {
  name: "BlogEdit",

  data() {
    return {

      types: {},
      ruleForm: {
        id: '',
        title: '',
        description: '',
        content: '',

        firstPicture: '',

        typeId: '',
        createTime: '2020-05-22 22:05:49',
        updateTime: '2020-05-22 22:05:49',
        userId: 1,
        words: null,

        views: 0,

        status: 0,

      },

      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: 'blur'},
          {min: 2, max: 45, message: '长度在 3 到 45 个字符', trigger: 'blur'}
        ],
        description: [
          {required: true, message: '请输入摘要', trigger: 'blur'}
        ],
        content: [
          {trequired: true, message: '请输入内容', trigger: 'blur'}
        ],

        words: [{required: true, message: '请输入文章字数', trigger: 'change'}],

      }
    }
  },
  methods: {


    computeWords() {
      var contents = this.ruleForm.content
      this.ruleForm.words = contents.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, '').length
    },


    submitForm(formName) {

      this.$refs[formName].validate((valid) => {
        if (valid) {


          const _this = this
          this.$axios.post('/blog/edit', this.ruleForm, {
            headers: {
              "Authorization": localStorage.getItem("token")
            }
          }).then(res => {
            console.log(res)
            _this.$alert('操作成功', '提示', {
              confirmButtonText: '确定',
              callback: action => {
                _this.$router.push("/blogList")
              }
            });

          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    getTypes() {
      const _this = this
      this.$axios.get('/types').then(res => {
        _this.types = res.data.data
      })
      console.log(this.types)
    },
    getBlogById() {
      const blogId = this.$route.params.blogId

      const _this = this
      if (blogId) {
        this.$axios.get('/blog/' + blogId).then(res => {
          const blog = res.data.data
          _this.ruleForm.id = blog.id
          _this.ruleForm.title = blog.title
          _this.ruleForm.firstPicture = blog.firstPicture
          _this.ruleForm.description = blog.description
          _this.ruleForm.content = blog.content
          _this.ruleForm.words = blog.words
          _this.ruleForm.views = blog.views
          _this.ruleForm.typeId = blog.typeId
          _this.ruleForm.status = blog.status
          _this.ruleForm.userId = blog.userId
          _this.ruleForm.createTime = blog.createTime
          _this.ruleForm.updateTime = blog.updateTime

        })

      }
    }

  },
  created() {
    this.getTypes();
    this.getBlogById();


  }
}
</script>

<style scoped>
.m-content {
  width: 100%;

}
</style>