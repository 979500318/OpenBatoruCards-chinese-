package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_W3_YukayukaThreee extends Card {
    
    public LRIG_W3_YukayukaThreee()
    {
        setImageSets("WXDi-P08-007", "WXDi-P08-007U", Mask.IGNORE+"WXDi-P179P");
        
        setOriginalName("ゆかゆか☆さ～ん");
        setAltNames("ユカユカサーン Yukayuka Saan Yukayuka Three");
        setDescription("jp",
                "@U：対戦相手のシグニ１体がアタックしたとき、対戦相手が%Xを支払わないかぎり、ターン終了時まで、そのシグニは能力を失う。\n" +
                "@E：対戦相手のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@A $G1 %W0：以下を３回行う。「対戦相手が手札を１枚捨てるか%Xを支払わないかぎり、あなたは自分のデッキの上からカードを３枚見る。その中からカードを１枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。」"
        );
        
        setName("en", "Yukayuka☆Threee");
        setDescription("en",
                "@U: Whenever a SIGNI on your opponent's field attacks, it loses its abilities until end of turn unless your opponent pays %X.\n" +
                "@E: Return target SIGNI on your opponent's field to its owner's hand.\n" +
                "@A $G1 %W0: Perform the following three times. \"Look at the top three cards of your deck unless your opponent discards a card or pays %X. Add up to one card from among them to your hand and put the rest on the bottom of your deck in any order.\""
        );
        
        setName("en_fan", "Yukayuka☆Three~e");
        setDescription("en_fan",
                "@U: Whenever your opponent's SIGNI attacks, until end of turn, that SIGNI loses its abilities unless your opponent pays %X.\n" +
                "@E: Target 1 of your opponent's SIGNI, and return it to their hand.\n" +
                "@A $G1 %K0: Do the following 3 times: \"Unless your opponent discards a card or pays %X, you look at the top 3 cards of your deck. Add up to 1 card from among them to your hand, and put the rest on the bottom of your deck in any order.\""
        );
        
		setName("zh_simplified", "由香香☆叁～");
        setDescription("zh_simplified", 
                "@U 当对战对手的精灵1只攻击时，如果对战对手不把%X:支付，那么直到回合结束时为止，那只精灵的能力失去。\n" +
                "@E :对战对手的精灵1只作为对象，将其返回手牌。\n" +
                "@A $G1 %W0:以下进行3次。\n" +
                "@>:如果对战对手不把手牌1张舍弃或支付%X，那么你从自己的牌组上面看3张牌。从中把牌1张最多加入手牌，剩下的任意顺序放置到牌组最下面。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.YUKAYUKA);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!payEner(getOpponent(), Cost.colorless(1)))
            {
                disableAllAbilities(caller, AbilityGain.ALLOW, ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            addToHand(target);
        }
        
        private void onActionEff()
        {
            for(int i=0;i<3;i++)
            {
                if(!pay(getOpponent(), new DiscardCost(0,1), new EnerCost(Cost.colorless(1))))
                {
                    look(3);
                    
                    CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
                    addToHand(cardIndex);
                    
                    while(getLookedCount() > 0)
                    {
                        cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                        returnToDeck(cardIndex, DeckPosition.BOTTOM);
                    }
                }
            }
        }
    }
}
