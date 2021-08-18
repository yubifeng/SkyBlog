<template>
  <div v-if="ifShow">

    <!--添加-->
    <el-form inline>
      <el-form-item>
        <el-button type="primary" size="small" icon="el-icon-plus" @click="addDialogVisible=true">添加用户</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="userList">
      <el-table-column label="序号" type="index" width="50"></el-table-column>
      <el-table-column label="头像" width="80">
        <template v-slot="scope">
          <el-avatar shape="square" :size="60" fit="contain" :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column label="用户名" prop="username"></el-table-column>
      <el-table-column label="昵称" prop="nickname"></el-table-column>
      <el-table-column label="邮箱" prop="email"></el-table-column>
      <el-table-column label="创建时间" prop="createTime"></el-table-column>
      <el-table-column label="更新时间" prop="updateTime"></el-table-column>
      <el-table-column label="是否禁用" width="100">
        <template v-slot="scope">
          <el-switch v-model="scope.row.status" :active-value="1" :inactive-value="0"  @change="userPublishedChanged(scope.row.id)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="角色" prop="role" width="100"></el-table-column>



      <el-table-column label="操作" width="200">
        <template v-slot="scope">
          <el-button icon="el-icon-edit" size="mini" type="primary" @click="showEditDialog(scope.row)">编辑</el-button>
          <el-popconfirm icon="el-icon-delete" iconColor="red" title="确定删除吗？" @confirm="deleteUserById(scope.row.id)">
            <el-button slot="reference" icon="el-icon-delete" size="mini" type="danger">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.currentPage"
                   :page-sizes="[10, 20, 30, 50]" :page-size="queryInfo.pageSize" :total="total"
                   layout="total, sizes, prev, pager, next, jumper" background>
    </el-pagination>



    <!--添加友链对话框-->
    <el-dialog title="添加用户" width="40%" :visible.sync="addDialogVisible" :close-on-click-modal="false" @close="addDialogClosed">
      <!--内容主体-->
      <el-form :model="addForm" :rules="formRules" ref="addFormRef" label-width="80px">

        <el-form-item label="用户名" prop="username">
          <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="addForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addForm.password"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="addForm.email"></el-input>
        </el-form-item>
        <el-form-item label="头像URL" prop="avatar">
          <el-input v-model="addForm.avatar"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-input v-model="addForm.role"></el-input>
        </el-form-item>
        <el-form-item label="是否公开" prop="published">
          <el-switch v-model="addForm.status" :active-value="1" :inactive-value="0" ></el-switch>
        </el-form-item>
      </el-form>
      <!--底部-->
      <span slot="footer">
				<el-button @click="addDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="saveUser">确 定</el-button>
			</span>
    </el-dialog>

    <!--编辑用户对话框-->
    <el-dialog title="编辑账户" width="40%" :visible.sync="editDialogVisible" :close-on-click-modal="false" @close="editDialogClosed">
      <!--内容主体-->
      <el-form :model="editForm" :rules="formRules" ref="editFormRef" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="editForm.username"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="editForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="editForm.password"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
        <el-form-item label="头像URL" prop="avatar">
          <el-input v-model="editForm.avatar"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-input v-model="editForm.role"></el-input>
        </el-form-item>
        <el-form-item label="是否公开" prop="published">
          <el-switch v-model="editForm.status" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
      </el-form>
      <!--底部-->
      <span slot="footer">
				<el-button @click="editDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="editUser">确 定</el-button>
			</span>
    </el-dialog>
  </div>

</template>

<script>
export default {
  name: "UserManager",
  data() {
    return {
      infoForm: {
        content: '',
        commentEnabled: true,
      },
      queryInfo: {
        currentPage: 1,
        pageSize: 10
      },
      ifShow:false,
      userList: [],
      total: 0,
      addDialogVisible: false,
      editDialogVisible: false,
      addForm: {
        nickname: '',
        description: '',
        website: '',
        avatar: '',
        isPublished: true
      },
      editForm: {
        nickname: '',
        description: '',
        website: '',
        avatar: '',
        isPublished: true
      },
      formRules: {
        nickname: [{required: true, message: '请输入昵称', trigger: 'blur'}],
        description: [{required: true, message: '请输入描述', trigger: 'blur'}],
        website: [{required: true, message: '请输入网站', trigger: 'blur'}],
        avatar: [{required: true, message: '请输入头像URL', trigger: 'blur'}],
      }
    }
  },
  methods:{
    //获取当前分页的友链
    getUserList() {
      const _this = this
      this.$axios.get('/user/list?currentPage=' + this.queryInfo.currentPage+"&pageSize=" + this.queryInfo.pageSize).then((res) => {
        _this.userList = res.data.data.records
        _this.total = res.data.data.total
      });
    },

    handleCurrentChange(newPage) {
      this.queryInfo.currentPage = newPage
      this.getUserList()
    },
    handleSizeChange(newPageSize){
      this.queryInfo.pageSize = newPageSize
      this.getUserList()

    },
    showEditDialog(row){
      this.editForm = row
      this.editDialogVisible = true
    },
    editDialogClosed() {
      this.editForm = {}
      this.editDialogVisible = false
    },

    deleteUserById(id) {
      const _this = this
      this.$axios.get('/user/delete/' + id).then((res) => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.getUserList()
            //_this.$router.push("/blogList")
          }
        })
      })
    },


    userPublishedChanged(id){
      const _this = this
      this.$axios.get('/user/publish/'+id).then((res) => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.getUserList()
            //_this.$router.push("/blogList")
          }
        })
      })

    },
    saveUser(){
      const _this = this
      this.$axios.post('/user/create',this.addForm).then((res) => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.addDialogVisible = false

            _this.getUserList()
            //_this.$router.push("/blogList")
          }
        })
      });
      this.$refs['addFormRef'].resetFields();
    },
    editUser(){
      const _this = this
      this.$axios.post('/user/update', this.editForm).then(res => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.editDialogVisible = false
            _this.getUserList()
          }
        });
      });

    },
    commentEnabledChanged(){

    },
    addDialogClosed(){
      this.addDialogVisible = false
    },

  },
  created() {
    if(this.$store.getters.getUser.username){
      if(this.$store.getters.getUser.role=="role_root"){
        this.ifShow = true
        this.getUserList()
      }
      else {
        this.$message.error("你没有权限")
      }
    }
    else {
      this.$message.error("你没有权限")
    }




  }
}
</script>