<template>
  <div>

    <!--搜索-->
    <el-row>
      <el-col :span="6">
        <el-select v-model="queryInfo.blogId" placeholder="请选择页面" :filterable="true" :clearable="true" @change="search" size="small" style="min-width: 400px">
          <el-option :label="item.title" :value="item.id" v-for="item in blogList" :key="item.id"></el-option>
        </el-select>
      </el-col>
    </el-row>

    <el-table :data="commentList" row-key="id" :tree-props="{children: 'replyComments'}">
      <el-table-column label="序号" type="index" width="50"></el-table-column>
      <el-table-column label="昵称" prop="nickname">
        <template v-slot="scope">
          {{ scope.row.nickname }}
          <el-tag v-if="scope.row.isAdminComment===1" size="mini" effect="dark" style="margin-left: 5px">我</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="头像" width="80">
        <template v-slot="scope">
          <el-avatar shape="square" :size="60" fit="contain" :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column label="邮箱"  prop="email" show-overflow-tooltip></el-table-column>
      <el-table-column label="网站" prop="website" show-overflow-tooltip></el-table-column>
      <el-table-column label="ip" prop="ip" width="130"></el-table-column>
      <el-table-column label="评论内容" prop="content" show-overflow-tooltip></el-table-column>
      <el-table-column label="QQ" prop="qq" width="115"></el-table-column>
      <el-table-column label="博客id" show-overflow-tooltip>
        <template v-slot="scope">
          <el-link type="success" :href="`/blog/${scope.row.blogId}`" >{{ scope.row.blogId }}</el-link>

        </template>
      </el-table-column>
      <el-table-column label="发表时间" width="170">
        <template v-slot="scope">{{ scope.row.createTime}}</template>
      </el-table-column>
      <el-table-column label="是否公开" width="80">
        <template v-slot="scope">
          <el-switch v-model="scope.row.isPublished" @change="commentPublishedChanged(scope.row.id)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template v-slot="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row)">编辑</el-button>
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteCommentById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pageNum"
                   :page-sizes="[10, 20, 30, 50]" :page-size="queryInfo.pageSize" :total="total"
                   layout="total, sizes, prev, pager, next, jumper" background>
    </el-pagination>

    <!--编辑评论对话框-->
    <el-dialog title="编辑评论" width="50%" :visible.sync="editDialogVisible" :close-on-click-modal="false" @close="editDialogClosed">
      <!--内容主体-->
      <el-form :model="editForm" :rules="formRules" ref="editFormRef" label-width="80px">
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="editForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="头像" prop="avatar">
          <el-input v-model="editForm.avatar"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
        <el-form-item label="网站" prop="website">
          <el-input v-model="editForm.website"></el-input>
        </el-form-item>
        <el-form-item label="ip" prop="ip">
          <el-input v-model="editForm.ip"></el-input>
        </el-form-item>
        <el-form-item label="评论内容" prop="content">
          <el-input v-model="editForm.content" type="textarea" maxlength="255" :rows="5" show-word-limit></el-input>
        </el-form-item>
      </el-form>
      <!--底部-->
      <span slot="footer">
				<el-button @click="editDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="editComment">确 定</el-button>
			</span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "CommentList",
  data() {
    return {
      pageId: null,
      queryInfo: {
        page: null,
        blogId: null,
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      commentList: [],
      blogList: [],
      editDialogVisible: false,
      editForm: {
        id: null,
        nickname: '',
        avatar: '',
        email: '',
        website: null,
        ip: '',
        content: ''
      },
      formRules: {
        nickname: [{required: true, message: '请输入评论昵称', trigger: 'blur'}],
        avatar: [{required: true, message: '请输入评论头像', trigger: 'blur'}],
        email: [
          {required: true, message: '请输入评论邮箱', trigger: 'blur'},
        ],
        ip: [
          {required: true, message: '请输入评论ip', trigger: 'blur'},
          // {validator: checkIpv4, trigger: 'blur'}
        ],
        content: [
          {required: true, message: '请输入评论内容', trigger: 'blur'},
          {max: 255, message: '评论内容不可多于255个字符', trigger: 'blur'}
        ],
      }
    }
  },
  methods:{
    //分页获取评论列表
    getCommentList() {
      const _this = this
      this.$axios.get('/commentList?currentPage=' + this.queryInfo.pageNum+"&pageSize=" + this.queryInfo.pageSize,{
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then((res) => {
        _this.commentList = res.data.data.records
        _this.total = res.data.data.total
        //console.log(_this.blogList)
      });
    },
    //获取所有博客列表
    getBlogList() {
      const _this = this
      this.$axios.get('/blog/all',{
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then((res) => {
        _this.blogList = res.data.data
        //console.log(_this.blogList)
      });
    },
    //监听页码改变事件
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage
      this.getCommentList()
    },
    //监听 pageSize 改变事件
    handleSizeChange(newPageSize){
      this.queryInfo.pageSize = newPageSize
      this.getCommentList()

    },

    //切换评论公开状态
    commentPublishedChanged(id){
      const _this = this
      this.$axios.get('/comment/publish/'+id).then((res) => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.page(_this.getCommentList())
            //_this.$router.push("/blogList")
          }
        })
      });

    },



    showEditDialog(row){
      this.editDialogVisible = true
      this.editForm = row

    },
    editDialogClosed(){
      this.editDialogVisible = false
    },
    deleteCommentById(id){
      const _this = this
      this.$axios.get('/comment/delete/'+id).then((res) => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
             _this.getCommentList()
            //_this.$router.push("/blogList")
          }
        })
      });

    },
    editComment(){
      const _this = this
      this.$axios.post('/comment/update',this.editForm).then((res) => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.getCommentList()
            _this.editDialogVisible = false
            //_this.$router.push("/blogList")
          }
        })
      });
    },
    search(){
      const _this = this
      this.$axios.get('/comment/detail?blogId='+this.queryInfo.blogId+"&currentPage=" + this.queryInfo.pageNum+"&pageSize=" + this.queryInfo.pageSize,{
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then((res) => {
        _this.commentList = res.data.data.records
        _this.total = res.data.data.total
        //console.log(_this.blogList)
      });

    }


  },
  created() {
    this.getCommentList()
    this.getBlogList()


  }

}
</script>

<style scoped>





</style>