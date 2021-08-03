<template>
  <div>

    <!--添加-->
    <el-form inline>
      <el-form-item>
        <el-button type="primary" size="small" icon="el-icon-plus" @click="addDialogVisible=true">添加分类</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="typeList">
      <el-table-column label="序号" type="index" width="300"></el-table-column>
      <el-table-column label="名称" prop="typeName"></el-table-column>


      <el-table-column label="操作" width="500">
        <template v-slot="scope">
          <el-button icon="el-icon-edit" size="mini" type="primary" @click="showEditDialog(scope.row)">编辑</el-button>
          <el-popconfirm icon="el-icon-delete" iconColor="red" title="确定删除吗？" @confirm="deleteTypeById(scope.row.id)">
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
    <el-dialog title="添加分类" width="40%" :visible.sync="addDialogVisible" :close-on-click-modal="false" @close="addDialogClosed">
      <!--内容主体-->
      <el-form :model="addForm" :rules="formRules" ref="addFormRef" label-width="80px">
        <el-form-item label="昵称" prop="typeName">
          <el-input v-model="addForm.typeName"></el-input>
        </el-form-item>
      </el-form>
      <!--底部-->
      <span slot="footer">
				<el-button @click="addDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="saveType">确 定</el-button>
			</span>
    </el-dialog>

    <!--编辑友链对话框-->
    <el-dialog title="编辑分类" width="40%" :visible.sync="editDialogVisible" :close-on-click-modal="false" @close="editDialogClosed">
      <!--内容主体-->
      <el-form :model="editForm" :rules="formRules" ref="editFormRef" label-width="80px">
        <el-form-item label="名称" prop="typeName">
          <el-input v-model="editForm.typeName"></el-input>
        </el-form-item>
      </el-form>
      <!--底部-->
      <span slot="footer">
				<el-button @click="editDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="editType">确 定</el-button>
			</span>
    </el-dialog>
  </div>

</template>

<script>
export default {
  name: "Category",
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
      typeList: [],
      total: 0,
      addDialogVisible: false,
      editDialogVisible: false,
      addForm: {
        typeName: '',
      },
      editForm: {
        typeName: '',
      },
      formRules: {
        typeName: [{required: true, message: '名称', trigger: 'blur'}],
      }
    }
  },
  methods:{
    //获取当前分页的友链
    getTypeList() {
      const _this = this
      this.$axios.get('/type/list?currentPage=' + this.queryInfo.currentPage+"&pageSize=" + this.queryInfo.pageSize).then((res) => {
        _this.typeList = res.data.data.records
        _this.total = res.data.data.total
        //console.log(_this.blogList)
      });
    },

    handleCurrentChange(newPage) {
      this.queryInfo.currentPage = newPage
      this.getFriendList()
    },
    handleSizeChange(newPageSize){
      this.queryInfo.pageSize = newPageSize
      this.getFriendList()

    },
    showEditDialog(row){
      this.editForm = row
      this.editDialogVisible = true
    },
    editDialogClosed() {
      this.editForm = {}
      this.editDialogVisible = false
    },

    deleteTypeById(id) {
      const _this = this
      this.$axios.get('/type/delete/' + id).then((res) => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.getTypeList()
            //_this.$router.push("/blogList")
          }
        })
      })
    },



    saveType(){
      const _this = this
      this.$axios.post('/type/create',this.addForm).then((res) => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.addDialogVisible = false

            _this.getTypeList()
            //_this.$router.push("/blogList")
          }
        })
      });
      this.$refs['addFormRef'].resetFields();
    },
    editType(){
      const _this = this
      this.$axios.post('/type/update', this.editForm).then(res => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.editDialogVisible = false
            _this.getTypeList()
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
    this.getTypeList()
  }
}


</script>

<style scoped>
.el-button + span {
  margin-left: 10px;
}


.el-form--inline .el-form-item {
  margin-bottom: 0;
}


</style>