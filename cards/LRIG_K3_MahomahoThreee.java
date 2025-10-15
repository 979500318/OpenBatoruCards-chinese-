package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_K3_MahomahoThreee extends Card {
    
    public LRIG_K3_MahomahoThreee()
    {
        setImageSets("WXDi-P07-007", "WXDi-P07-007U");
        
        setOriginalName("まほまほ☆さ～ん");
        setAltNames("マホマホサーン Mahomaho Saan Mahomaho Three");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手は以下の２つから１つを選び、あなたはそれを行う。\n" +
                "$$1対戦相手のデッキの上からカードを４枚トラッシュに置く。\n" +
                "$$2あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。\n" +
                "@E：あなたのトラッシュからシグニ１枚を対象とし、それを場に出す。\n" +
                "@A $G1 %K0：以下を３回行う。「対戦相手は手札を１枚捨てるか%Xを支払わないかぎり、自分のデッキの上からカードを４枚トラッシュに置く。」"
        );
        
        setName("en", "Mahomaho☆Threee");
        setDescription("en",
                "@U: At the beginning of your attack phase, your opponent chooses one of the following and you perform it.\n" +
                "$$1 Put the top four cards of your opponent's deck into their trash.\n" +
                "$$2 Add target SIGNI from your trash to your hand.\n" +
                "@E: Put target SIGNI from your trash onto your field.\n" +
                "@A $G1 %K0: Perform the following three times. \"Your opponent puts the top four cards of their deck into their trash unless they discard a card or pay %X.\""
        );
        
        setName("en_fan", "Mahomaho☆Three~e");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, @[@|your opponent chooses 1 of the following|@]@, and you do it:\n" +
                "$$1 Put the top 4 cards of your opponent's deck into the trash.\n" +
                "$$2 Target 1 SIGNI from your trash, and add it to your hand.\n" +
                "@E: Target 1 SIGNI from your trash, and put it onto the field.\n" +
                "@A $G1 %K0: Do the following 3 times: \"Put the top 4 cards of your opponent's deck into the trash unless they discard 1 card from their hand or pay %X.\""
        );
        
		setName("zh_simplified", "真帆帆☆叁～");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手从以下的2种选1种，你将其进行。\n" +
                "$$1 从对战对手的牌组上面把4张牌放置到废弃区。\n" +
                "$$2 从你的废弃区把精灵1张作为对象，将其加入手牌。\n" +
                "@E :从你的废弃区把精灵1张作为对象，将其出场。\n" +
                "@A $G1 %K0:以下进行3次。\n" +
                "@>:对战对手如果不把手牌1张舍弃或支付%X，那么从自己的牌组上面把4张牌放置到废弃区。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MAHOMAHO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(playerChoiceMode(getOpponent()) == 1)
            {
                millDeck(getOpponent(), 4);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
                addToHand(target);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable()).get();
            putOnField(target);
        }
        
        private void onActionEff()
        {
            for(int i=0;i<3;i++)
            {
                if(!pay(getOpponent(), new DiscardCost(0,1), new EnerCost(Cost.colorless(1))))
                {
                    millDeck(getOpponent(), 4);
                }
            }
        }
    }
}
