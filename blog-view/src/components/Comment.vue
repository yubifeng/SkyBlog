<template>

  <!--评论列表-->
  <div class="comment-body">
    <CommentForm v-if="parentCommentId===-1" :realParentCommentId = "-1" @parentEvent="toClick"></CommentForm>


    <h3 class="ui-dividing-header">Comments | {{ commentCount }} 条评论</h3>
    <h3 class="ui-header" v-if="commentCount===0">快来抢沙发！</h3>




    <div class="comment" v-for="comment in comments" :key="comment.id">

      <span class="anchor" :id="`comment-${comment.id}`"></span>

      <div class="image-avatar" >
        <img :src="comment.avatar" style="width: 40px;border-radius:50%;">
      </div>



      <div class="content">
        <a class="nickname" :href="comment.website!=''&&comment.website!=null?comment.website:null" target="_blank" rel="external nofollow noopener">{{ comment.nickname }}</a>
        <el-tag class="label" type="info" size="mini" effect="dark" v-if="comment.isAdminComment==1">博主</el-tag>

        <span class="date">{{ comment.createTime  }}</span>

        <el-button size="mini" type="primary" @click="setReply(comment.id)">回复</el-button>

        <div class="text" v-html="comment.content"></div>
      </div>







      <div class="comments" v-if="comment.replyComments.length>0">
        <div class="comment" v-for="reply in comment.replyComments" :key="reply.id">
          <span class="anchor" :id="`comment-${reply.id}`"></span>

          <div class="image-avatar" >
            <img :src="reply.avatar" style="width: 40px;border-radius:50%;">
          </div>

          <div class="content">
            <a class="nickname" :href="reply.website!=''&&reply.website!=null?reply.website:null" target="_blank" rel="external nofollow noopener">{{ reply.nickname }}</a>
            <el-tag class="label" type="info" size="mini" effect="dark" v-if="comment.isAdminComment==1">博主</el-tag>

            <span class="date">{{ reply.createTime }}</span>


            <div class="text">
              <a  style="text-decoration-line: none;margin-right: 8px;font-weight: bold;color: #333333" :href="`#comment-${comment.id}`">@{{ reply.parentCommentNickname }}</a>
              <div style="display: inline" v-html="reply.content">
              </div>
            </div>


            <div class="actions">
              <el-button size="mini" type="primary" @click="setChildrenReply(reply.id)">回复</el-button>
            </div>
          </div>
          <CommentForm v-if="parentCommentId===reply.id" :realParentCommentNickname="reply.nickname" :realParentCommentId = "comment.id" @parentEvent="toClick"/>
        </div>
      </div>





      <CommentForm v-if="parentCommentId===comment.id" :realParentCommentNickname="comment.nickname" :realParentCommentId = "comment.id"  @parentEvent="toClick"/>
    </div>





  </div>




</template>

<script>
import CommentForm from "@/components/CommentForm";
export default {
  name: "Comment",
  components: {CommentForm},

  data(){
    return {
      commentCount: 0,
      comments: [],
      parentCommentId:-1,
      blogId: 0,



    }

  },

  methods: {
    setReply(id) {
      this.parentCommentId = id;

    },

    setChildrenReply(id) {
      this.parentCommentId = id;

     },



    //接受子组件信息，将评论表单归位
    toClick(msg) {
      //console.log(msg)
      this.parentCommentId = -1
    },










    getComments() {

      if(this.$route.params.blogId){
         this.blogId = this.$route.params.blogId
      }
      else if(this.$route.path == "/about"){
        this.blogId = 1
      }
      else if(this.$route.path == "/friends"){
        this.blogId = 11
      }
      else{
        alert("error")
        return false
      }


      const _this = this
      this.$axios.get('/comment/' + this.blogId).then(res => {
        _this.comments = res.data.data
        //console.log(_this.comments)
        _this.commentCount = _this.comments.length
        for( var i in _this.comments) {
          _this.commentCount = _this.commentCount + _this.comments[i].replyComments.length;
         // window.console.log(i)
        }
      })






    },



  },
  created() {
    this.getComments()


  }
}
</script>

<style scoped>
.comment-body {
  padding: 5px 35px 15px 22px;
}
.comment {
  padding-right: 2em !important;
  padding-left: 1em !important;
  margin: 7px 0px 0px;
  padding-top: 7px;


}
.comments {
  padding: 42px 0px 24px 31.5px;
  margin: -28px 0px -10px 17.5px;

  border-left: 1px #ccc solid;


}

.content {
  margin-left: 0px;
  display: inline-block;
  width: auto;
  vertical-align:top;


}
.image-avatar {
  display: inline-block;
  width: 49px;
  vertical-align:top;





}
.label {
  margin-left: 5px;




}
.nickname {
  font-weight: bolder;
  font-size: 14px;
  color: #000;
  text-decoration-line: none;
}
.comment .el-button {
  margin-left: 5px;
  padding: 4px 5px;
}

/*.comments .comment  {*/
/*  white-space: pre-wrap !important;*/
/*  margin-top: 7px;*/
/*  line-height: 1.5;*/
/*}*/
.text {
  white-space: pre-wrap !important;
  margin: 7px 0px;
  line-height: 1.5;
  font-size: 14px;
}
.date {
  color: gray ;
  font-size: 10px;
  margin-left: 5px;
}



.anchor {

  height: 55px;
  margin-top: -55px;
  visibility: hidden;
}




</style>