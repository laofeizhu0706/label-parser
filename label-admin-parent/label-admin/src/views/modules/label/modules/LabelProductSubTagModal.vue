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
          label="用户标签">
          <a-select
            mode="multiple"
            style="width: 100%"
            placeholder="请选择用户标签"
            v-decorator="['subTag', validatorRules.subTag ]"
          >
            <a-select-option v-for="(tag,index) in selectData" :key="(index + 9).toString(36) + index" :value="tag.name">
              {{ tag.name }}
            </a-select-option>
          </a-select>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "LabelProductSubTagModal",
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        selectData:[],
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        name:{rules: [{ required: true, message: '请输入标签名称!' }]},
        subTag:{rules: [{ required: true, message: '请输入相关联标签!' }]},
        },
        url: {
          add: "/label/labelProductSubTag/add",
          edit: "/label/labelProductSubTag/edit",
          listUserTag:"/label/labelUserSubTag/list"
        },
      }
    },
    created () {
      httpAction(this.url.listUserTag,{"pageNo":1,"pageSize":99999},'get').then((res)=>{
        if(res.success){
          this.selectData=res.result.records;
        }
      });
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.model.subTag=this.model.subTag.split(",")
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
            formData.subTag=formData.subTag.join()
            console.log(formData)
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


    }
  }
</script>

<style lang="less" scoped>

</style>