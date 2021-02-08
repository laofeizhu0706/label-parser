<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
      
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="标签名称">
          <a-input placeholder="请输入标签名称" v-decorator="['name', validatorRules.name ]" />
        </a-form-item>
        <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="相关联标签">
            <a-col v-for="(tag,index) in tags" :key="index">
              <a-tag :key="tag" :closable="true" @close="() => handleClose(tag)" style="height: auto;padding: 4px 18px;">
                <labelUserSubTag-model :ref="'models'+tag"/>
              </a-tag>
            </a-col>
            <a-tag style="background: #fff; borderStyle: dashed;" @click="addTag">
                <a-icon type="plus" /> 新增规则
            </a-tag>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import LabelUserSubTagModel from './LabelUserSubTagModel'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "LabelUserSubTagModal",
    components: {
      LabelUserSubTagModel
    },
    data () {
      return {
        title:"操作",
        visible: false,
        tags: [],
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        name:{rules: [{ required: true, message: '请输入标签名称!' }]},
        subTag:{rules: [{ required: true, message: '请输入相关联标签!' }]},
        },
        url: {
          add: "/label/labelUserSubTag/add",
          edit: "/label/labelUserSubTag/edit",
        },
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'name','subTag'))
		  //时间格式化
        });

      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
            let subTag = this.getSubData();
            formData.subTag=subTag
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
        })
      },
      handleCancel () {
        this.close()
      },
      handleClose(removedTag) {
        const tags = this.tags.filter(tag => tag !== removedTag);
        this.tags = tags;
      },

      addTag() {
        let tags = this.tags;
        tags = [...tags, tags.length+1];
        console.log(tags);
        Object.assign(this, {
          tags,
          inputVisible: false,
          inputValue: '',
        });
      },
      getSubData() {
        let arr = [];
        for(let index in this.$refs) {
           arr.push(this.$refs[index][0].getData());
        }
        return arr.join();
      },

    }
  }
</script>

<style lang="less" scoped>

</style>