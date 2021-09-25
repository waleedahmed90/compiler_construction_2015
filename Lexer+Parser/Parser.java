
//Parser Class (Roll#: 16-10876)
public class Parser {
	Lex tempLex;
	TokenInfo nextToken;
	//constructor
	public Parser(String fileName) {
		this.tempLex = new Lex(fileName);
		nextToken = tempLex.gettoken();
	}
	//Parse Beginning Method
	public void Parse() {
		tiny_java_program();
		if (this.nextToken.tok != Token.EOF) {
			errorMessage("Cannot Identify Characters");
		}
	}

	//////// method to consume token
	public void consumeToken() {
		this.nextToken = this.tempLex.gettoken();
	}
	//generic error generation method
	public void errorMessage(String msg) {
		System.out.println("Error: " + msg + ": (Line#: " + nextToken.line + ")");
	}

	// tiny_java_program ->class_decl

	void tiny_java_program() {
		class_decl();
	}

	// class_decl->"public" "class" identifier "{"member_decl_list"}"

	void class_decl() {
		// public
		if (this.nextToken.tok == Token.KWPUBLIC) {
			this.consumeToken();
			// class
			if (this.nextToken.tok == Token.KWCLASS) {
				this.consumeToken();
				// identifier
				if (this.nextToken.tok == Token.ID) {
					this.consumeToken();
					// {
					if (this.nextToken.tok == Token.LBRACE) {
						this.consumeToken();
						// member_decl_list
						member_decl_list();
						// }
						if (this.nextToken.tok == Token.RBRACE) {
							this.consumeToken();
						} else {
							this.errorMessage("Right Brace } expected");
						}

					} else {
						this.errorMessage("Left Brace { expected");
					}

				} else {
					this.errorMessage("Identifier expected");
				}

			} else {
				this.errorMessage("Keyword class expected");
			}

		} else {
			this.errorMessage("Keyword public expected");
		}
	}

	// member_decl_list-> member_decl | member_decl member_decl_list

	public void member_decl_list() {
		// call member_decl
		member_decl();
		if (this.nextToken.tok == Token.KWPUBLIC) {
			this.consumeToken();
			// call itself
			member_decl_list();
		} else if (this.nextToken.tok == Token.KWSTATIC) {
			this.consumeToken();
			// call itself
			member_decl_list();
		}
	}

	//////////member_decl-> field_decl | method_decl
	
	public void member_decl() {
		if (this.nextToken.tok == Token.KWSTATIC) {
			this.consumeToken();
			if (this.nextToken.tok == Token.KWVOID) {
				this.consumeToken();
				method_decl();
			} else if ((this.nextToken.tok == Token.KWINT) || (this.nextToken.tok == Token.KWFLOAT)) {
				this.consumeToken();
				if (this.nextToken.tok == Token.ID) {
					this.consumeToken();
					if (this.nextToken.tok == Token.ASSIGNOP) {
						this.consumeToken();
						field_decl();
					} else if (this.nextToken.tok == Token.LPAR) {
						method_decl();
					} else {
						this.errorMessage("= or ( expected");
					}
				} else {
					this.errorMessage("Identifier expected");
				}
			} else {
				this.errorMessage("int or float expected");
			}

		} else if (this.nextToken.tok == Token.KWPUBLIC) {
			this.consumeToken();
			method_decl();
		} else {
			this.errorMessage("public or static expected");
		}

	}

	///////field_decl-> "static" return_type Identifier "("formal_param_list")"
	
	public void field_decl() {
		this.literal();//////////////////////// confused about consuming a token
						//////////////////////// here
		if (this.nextToken.tok == Token.SEMICOLON) {
			this.consumeToken();
		} else {
			this.errorMessage("; expected");
		}

	}

	//method_decl->.... 
	
	public void method_decl() {
		if (this.nextToken.tok == Token.ID) {
			this.consumeToken();
			if (this.nextToken.tok == Token.LPAR) {
				this.consumeToken();
				if (((this.nextToken.tok != Token.RPAR) && (this.nextToken.tok != Token.KWSTATIC))
						&& ((this.nextToken.tok == Token.KWINT) || (this.nextToken.tok == Token.KWFLOAT))) {
					formal_param_list();
					if (this.nextToken.tok == Token.RPAR) {
						this.consumeToken();
						if (this.nextToken.tok == Token.LBRACE) {
							this.consumeToken();
							method_body();
							if (this.nextToken.tok == Token.RBRACE) {
								this.consumeToken();
							} else {
								this.errorMessage("} expected");
							}
						} else {
							this.errorMessage("{ expected");
						}
					} else {
						this.errorMessage(") expected");
					}
				} else if (this.nextToken.tok == Token.RPAR) {
					this.consumeToken();
					if (this.nextToken.tok == Token.LBRACE) {
						this.consumeToken();
						method_body();
						if (this.nextToken.tok == Token.RBRACE) {
							this.consumeToken();
						} else {
							this.errorMessage("} expected");
						}

					} else {
						this.errorMessage("{ expected");
					}

				} else {
					this.errorMessage("Keyword int or float expected");
				}

			}

			else {
				this.errorMessage("( expected");
			}

		}

		else if (((this.nextToken.tok != Token.RPAR) && (this.nextToken.tok != Token.KWSTATIC))
				&& ((this.nextToken.tok == Token.KWINT) || (this.nextToken.tok == Token.KWFLOAT))) {
			formal_param_list();
			this.consumeToken();
			if (this.nextToken.tok == Token.RPAR) {
				this.consumeToken();
				if (this.nextToken.tok == Token.LBRACE) {
					this.consumeToken();
					method_body();
					if (this.nextToken.tok == Token.RBRACE) {
						this.consumeToken();
					} else {
						this.errorMessage("} expected");
					}

				} else {
					this.errorMessage("{ expected");
				}
			} else {
				this.errorMessage(") expected");
			}
		} else if (this.nextToken.tok == Token.RPAR) {
			this.consumeToken();
			if (this.nextToken.tok == Token.LBRACE) {
				this.consumeToken();
				method_body();
				if (this.nextToken.tok == Token.RBRACE) {
					this.consumeToken();
				} else {
					this.errorMessage("} expected");
				}

			} else {
				this.errorMessage("{ expected");
			}

		} else if (this.nextToken.tok == Token.KWSTATIC) {
			this.consumeToken();
			if (this.nextToken.tok == Token.KWVOID) {
				this.consumeToken();
				if (this.nextToken.tok == Token.ID) {
					this.consumeToken();
					if (this.nextToken.tok == Token.LPAR) {
						this.consumeToken();
						formal_param_list();
						if (this.nextToken.tok == Token.RPAR) {
							this.consumeToken();
							if (this.nextToken.tok == Token.LBRACE) {
								this.consumeToken();
								method_body();
								if (this.nextToken.tok == Token.RBRACE) {
									this.consumeToken();
								} else {
									this.errorMessage("} expected");
								}

							} else {
								this.errorMessage("{ expected");
							}

						} else {
							this.errorMessage(") expected");
						}

					} else {
						this.errorMessage("( expected");
					}

				} else {
					this.errorMessage("Identifier expected");
				}

			} else {
				this.errorMessage("void expected");
			}

		} else {
			this.errorMessage("static or ) or identifier expected");
		}

	}

	//literal-> DecimalIntegerLiteral| FloatingPointLiteral| StringLiteral
	
	void literal() {
		if (this.nextToken.tok == Token.INTEGERCONSTANT)
			this.consumeToken();
		else if (this.nextToken.tok == Token.FLOATCONSTANT)
			this.consumeToken();
		else if (this.nextToken.tok == Token.STRINGCONSTANT)
			this.consumeToken();
		else
			this.errorMessage("type constant expected");
	}

	//method_body-> local_decl| method_statement_list
	
	public void method_body() {
		local_decl();
		method_statement_list();
	}

	//formal_param_list-> formal_param| formal_param "," forma_param_list 
	
	public void formal_param_list() {
		formal_param();
		if (this.nextToken.tok == Token.COMMA) {
			formal_param_list();
		}
	}

	//formal_param-> type identifier
	
	public void formal_param() {
		type();
		if (this.nextToken.tok == Token.ID) {
			this.consumeToken();
		} else {
			this.errorMessage("Identifier expected");
		}
	}

	//type-> "int"| "float"
	
	public void type() {
		if (this.nextToken.tok == Token.KWINT) {
			this.consumeToken();
		} else if (this.nextToken.tok == Token.KWFLOAT) {
			this.consumeToken();
		} else {
			this.errorMessage("int or float data type expected");
		}
	}

	//return_type-> "void"| type
	
	void return_type() {
		if (this.nextToken.tok != Token.KWVOID) {
			type();
		} else if (this.nextToken.tok == Token.KWVOID) {
			this.consumeToken();
		} else {
			this.errorMessage("void expected");
		}
	}

	//local_decl_list-> local_decl local_decl_list|E
	
	public void local_decl_list() {
		local_decl();
		if (this.nextToken.tok == Token.KWINT) {
			this.consumeToken();
			local_decl();
		} else if (this.nextToken.tok == Token.KWFLOAT) {
			this.consumeToken();
			local_decl();
		} else {
			// do nothing dealing with the epsilon part
		}
	}

	//local_decl-> type identifier "=" literal ";"
	
	public void local_decl() {
		type();
		if (this.nextToken.tok == Token.ID) {
			this.consumeToken();
			if (this.nextToken.tok == Token.ASSIGNOP) {
				this.consumeToken();
				literal();
				if (this.nextToken.tok == Token.SEMICOLON) {
					this.consumeToken();
				} else {
					this.errorMessage("; expected");
				}
			} else {
				this.errorMessage("= symbol expected");
			}
		} else {
			this.errorMessage("identifier expected");
		}
	}

	//method_statement_list-> statement method_statement_list| return_statement
	
	public void method_statement_list() {
		statement();
		if (this.nextToken.tok == Token.SEMICOLON) {
			method_statement_list();
		} else if (this.nextToken.tok == Token.ID) {
			method_statement_list();
		} else if (this.nextToken.tok == Token.KWIF) {
			method_statement_list();
		} else if (this.nextToken.tok == Token.KWWHILE) {
			method_statement_list();
		} else if (this.nextToken.tok == Token.LBRACE) {
			method_statement_list();
		} else if (this.nextToken.tok == Token.KWRETURN) {
			return_statement();
		}
	}

	//statement_list->statement statement_list| E
	
	public void statement_list() {
		statement();
		if (this.nextToken.tok == Token.SEMICOLON) {
			statement_list();
		} else if (this.nextToken.tok == Token.ID) {
			statement_list();
		} else if (this.nextToken.tok == Token.KWIF) {
			statement_list();
		} else if (this.nextToken.tok == Token.KWWHILE) {
			statement_list();
		} else if (this.nextToken.tok == Token.LBRACE) {
			statement_list();
		} else {
			// deal the epsilon do nothing
		}
	}

	//.....statement->
	
	public void statement() {
		if(this.nextToken.tok == Token.SEMICOLON){
			this.consumeToken();
		}else if(this.nextToken.tok == Token.LBRACE){
			this.consumeToken();
			statement_list();
			if(this.nextToken.tok == Token.RBRACE){
				this.consumeToken();
			}else{
				this.errorMessage("} expected");
			}
		}else if(this.nextToken.tok == Token.KWWHILE){
			this.consumeToken();
			if(this.nextToken.tok == Token.LPAR){
				this.consumeToken();
				expression();
				if(this.nextToken.tok == Token.RPAR){
					this.consumeToken();
					statement();
				}else{
					this.errorMessage(") expected");
				}
			}
		}else if(this.nextToken.tok == Token.KWIF){
			this.consumeToken();
			if(this.nextToken.tok == Token.LPAR){
				this.consumeToken();
				expression();
				if(this.nextToken.tok == Token.RPAR){
					this.consumeToken();
					statement();
					if(this.nextToken.tok == Token.KWELSE){
						this.consumeToken();
						statement();
					}
				}else{
					this.errorMessage(") expected");
				}
			}
		}else if(this.nextToken.tok == Token.ID){
			this.consumeToken();
			if(this.nextToken.tok == Token.DOTOP){
				this.consumeToken();
				method_invocation();
				if(this.nextToken.tok == Token.SEMICOLON){
					this.consumeToken();
				}else{
					this.errorMessage("; expected");
				}
			}else if(this.nextToken.tok == Token.EQSYM){
				this.consumeToken();
				expression();
				if(this.nextToken.tok == Token.SEMICOLON){
					this.consumeToken();
				}else{
					this.errorMessage("; expected");
				}
			}
		}
	}

	//relational_expression
	
	public void relational_expression() {
		additive_expression();
		
		if(this.nextToken.tok == Token.LESS){
			this.consumeToken();
			additive_expression();
		}else if(this.nextToken.tok == Token.GREAT){
			this.consumeToken();
			additive_expression();
		}else if(this.nextToken.tok == Token.LESSEQ){
			this.consumeToken();
			additive_expression();
		}else if(this.nextToken.tok == Token.GREATEQ){
			this.consumeToken();
			additive_expression();
		}
		
	}

	//expression->...
	
	public void expression() {
		relational_expression();
		if (this.nextToken.tok == Token.NOTEQOP) {
			this.consumeToken();
			relational_expression();
		} else if (this.nextToken.tok == Token.EQSYM) {
			this.consumeToken();
			relational_expression();
		}
	}

	//return_statement->"return" expression ";"| "return" ";"
	
	public void return_statement() {
		if (this.nextToken.tok == Token.KWRETURN) {
			this.consumeToken();
		}

		if (this.nextToken.tok == Token.SEMICOLON) {
			this.consumeToken();
		} else {
			expression();
			if (this.nextToken.tok == Token.SEMICOLON) {
				this.consumeToken();
			}
		}
	}

	//qualified_name->identifier "." identifier|identifier
	
	public void qualified_name() {
		if (this.nextToken.tok == Token.ID) {
			this.consumeToken();
			if (this.nextToken.tok == Token.DOTOP) {
				this.consumeToken();
				if (this.nextToken.tok == Token.ID) {
					this.consumeToken();
				}
			}
		} else {
			this.errorMessage("identifier expected");
		}
	}

	//method_invocation->qualified_name"("argument_list")"|qualified_name"(" ")"
	
	public void method_invocation() {
		// if(this.nextToken.tok == Token.DOTOP){
		qualified_name();
		// }

		if (this.nextToken.tok == Token.LPAR) {
			this.consumeToken();
			argument_list();
			if (this.nextToken.tok == Token.RPAR) {
				this.consumeToken();
			} else {
				this.errorMessage(") expected");
			}
		} else {
			this.errorMessage("( expected");
		}

	}

	//argument_list->expression| expression ";" argument_list
	
	public void argument_list() {
		expression();
		if (this.nextToken.tok == Token.COMMA) {
			this.consumeToken();
			argument_list();
		} else {
			// do nothing
		}
	}

	//primary_expression->literal| Identifier| method_invocation| "("expression")"
	
	public void primary_expression() {
		if(this.nextToken.tok == Token.ID){
			this.consumeToken();
			if(this.nextToken.tok == Token.DOTOP){
				this.consumeToken();
				method_invocation();
			}else{
				//do nothing
			}
		}else if(this.nextToken.tok == Token.LPAR){
			this.consumeToken();
			expression();
			if(this.nextToken.tok == Token.RPAR){
				this.consumeToken();
			}else{
				this.errorMessage(") expected");
			}
		}else{
			literal();
		}
	}

	//unary_expression->....
	
	public void unary_expression() {
		if(this.nextToken.tok == Token.ADDOP){
			this.consumeToken();
			unary_expression();
		}else if(this.nextToken.tok == Token.SUBOP){
			this.consumeToken();
			unary_expression();
		}else if(this.nextToken.tok == Token.LPAR){
			this.consumeToken();
			type();
			if(this.nextToken.tok == Token.RPAR){
				this.consumeToken();
				unary_expression();
			}else{
				this.errorMessage(") expected");
			}
		}else{
			primary_expression();
		}
	}
	
	//additive_expression->multiplicative_expression| addPrime
	
	public void additive_expression(){
		multiplicative_expression();
		addPrime();
	}
	
	//addPrime->"+"multiplicative_expression addPrime| "-"multiplicative_expression addPrime| E
	
	public void addPrime(){
		if(this.nextToken.tok == Token.ADDOP){
			this.consumeToken();
			this.addPrime();
		}else if(this.nextToken.tok == Token.SUBOP){
			this.consumeToken();
			this.addPrime();
		
		}else{
			//do nothing. This part goes to epsilon
		}
	}
	
	//multiplicative_expression->unary_expression mulPrime
	
	public void multiplicative_expression(){
		unary_expression();
		mulPrime();
	}
	
	//mulPrime->"*"unary_expression mulPrime| "/"unary_expression mulPrime| E
	
	public void mulPrime(){
		if(this.nextToken.tok == Token.MULOP){
			unary_expression();
			mulPrime();
		}else if(this.nextToken.tok == Token.DIVOP){
			unary_expression();
			mulPrime();
		}else{
			//do nothing. This part goes to epsilon
		}
	}


}