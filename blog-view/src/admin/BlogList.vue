<template>
  <div class="bloglist-contain">
    <!--搜索，暂未实现-->
    <el-row>
      <el-col :span="8">
        <el-input v-model="queryInfo.title" :clearable="true" placeholder="请输入标题" size="small"
                  style="min-width: 500px" @clear="search" @keyup.native.enter="search">
          <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
        </el-input>
      </el-col>
    </el-row>
    <!--博客列表-->
    <el-table :data="blogList">
      <el-table-column label="序号" type="index" width="50"></el-table-column>
      <el-table-column label="标题" prop="title" show-overflow-tooltip></el-table-column>
      <el-table-column label="分类" prop="typeName" width="150"></el-table-column>
      <el-table-column label="创建时间" width="170">
        <template v-slot="scope">{{ scope.row.createTime }}</template>
      </el-table-column>
      <el-table-column label="最近更新" width="170">
        <template v-slot="scope">{{ scope.row.updateTime }}</template>
      </el-table-column>



      <el-table-column label="可见性" width="100">
        <template v-slot="scope">
          <el-switch v-model="scope.row.status"  :active-value="1" :inactive-value="0" @change="blogPublishedChanged(scope.row.id)"></el-switch>
        </template>
      </el-table-column>

      <el-table-column label="用户id" width="170">
        <template v-slot="scope">{{ scope.row.userId }}</template>
      </el-table-column>
      <!--删除和查看操作-->
      <el-table-column label="操作" width="200">
        <template v-slot="scope">
          <el-button icon="el-icon-edit" size="mini" type="primary" @click="goBlogEditPage(scope.row.id)">编辑</el-button>
          <el-popconfirm icon="el-icon-delete" iconColor="red" title="确定删除吗？" @confirm="deleteBlogById(scope.row.id)">
            <el-button slot="reference" icon="el-icon-delete" size="mini" type="danger">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <div  class="home-page">
      <!--分页-->
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pageNum"
                     :page-sizes="[10, 20, 30, 50]" :page-size="queryInfo.pageSize" :total="total"
                     layout="total, sizes, prev, pager, next, jumper" background>
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "BlogList",
  data() {
    return {
      queryInfo: {
        title: '',
        categoryId: null,
        pageNum: 1,
        pageSize: 10
      },
      blogList: [],
      total: 0,
      types: []
    }
  },
  created() {
    this.getTypes()
    this.getData();
  },
  methods: {
    //跳转到博客编辑页
    goBlogEditPage(blogId) {
      this.$router.push(`/blog/edit/${blogId}`)
    },
    //获取博客类型
    getTypes() {
      const _this = this
      this.$axios.get('/types').then(res => {
        _this.types = res.data.data
      })
      //console.log(this.types)
    },
    //获取当前分页的博客
    getData() {
      const _this = this
      this.$axios.get('/blogList?currentPage=' + this.queryInfo.pageNum+"&pageSize=" + this.queryInfo.pageSize,{
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then((res) => {
        _this.blogList = res.data.data.records
        _this.total = res.data.data.total
        for (var i in _this.blogList) {
          for (var j in _this.types) {
            if (_this.blogList[i].typeId == _this.types[j].id) {
              _this.blogList[i].typeName = _this.types[j].typeName
            }
          }
        }

      });

    },
    //通过博客id删除博客
    deleteBlogById(blogId) {
      const _this = this
      this.$axios.get('/blog/delete/' + blogId).then((res) => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {

            //_this.$router.push("/blogList")
            _this.getData()
          }
        })
      })
    },
    //未实现
    search() {
    },
    handleSizeChange(newPageSize) {
      this.queryInfo.pageSize = newPageSize;
      this.getData();

    },
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage
      this.getData()
    },



    blogPublishedChanged(blogId){
      const _this = this
      this.$axios.get('/blog/publish/'+blogId).then((res) => {
        _this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            _this.page(_this.currentPage)
            //_this.$router.push("/blogList")
          }
        })
      });


    }
  }
}
</script>
<style scoped>
.el-button + span {
  margin-left: 10px;
}
</style>