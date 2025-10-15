package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
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
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_G3_SangaVOGUE3 extends Card {
    
    public LRIG_G3_SangaVOGUE3()
    {
        setImageSets("WXDi-P03-030", "SPDi07-12","SPDi08-12");
        
        setOriginalName("VOGUE3 サンガ");
        setAltNames("ボーグスリーサンガ Boogu Surii Sanga");
        setDescription("jp",
                "=T ＜DIAGRAM＞\n" +
                "^U：あなたのアタックフェイズ開始時、あなたの場に青と緑と黒のシグニがある場合、[[エナチャージ１]]をし、その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。\n" +
                "@E：[[エナチャージ２]]\n" +
                "@A $G1 %G0：あなたの＜DIAGRAM＞のレベル１のルリグ１体を対象とし、それをルリグデッキに戻す。"
        );
        
        setName("en", "Sanga, Vogue 3");
        setDescription("en",
                "=T <<DIAGRAM>>\n" +
                "^U: At the beginning of your attack phase, if you have a blue SIGNI, green SIGNI, and black SIGNI on your field, [[Ener Charge 1]], then add up to one target SIGNI from your Ener Zone to your hand.\n" +
                "@E: [[Ener Charge 2]]\n" +
                "@A $G1 %G0: Return target level one <<DIAGRAM>> LRIG on your field to your LRIG deck. "
        );
        
        setName("en_fan", "Sanga, VOGUE 3");
        setDescription("en_fan",
                "=T <<DIAGRAM>>\n" +
                "^U: At the beginning of your attack phase, if there are blue, black and green SIGNI on your field, [[Ener Charge 1]], then, target up to 1 SIGNI from your ener zone, and add it to your hand.\n" +
                "@E: [[Ener Charge 2]].\n" +
                "@A $G1 %G0: Target 1 of your level 1 <<DIAGRAM>> LRIGs, and return it to the LRIG deck."
        );
        
		setName("zh_simplified", "VOGUE3 山河");
        setDescription("zh_simplified", 
                "=T<<DIAGRAM>>\n" +
                "^U:你的攻击阶段开始时，你的场上有蓝色和绿色和黑色的精灵的场合，[[能量填充1]]，然后，从你的能量区把精灵1张最多作为对象，将其加入手牌。\n" +
                "@E :[[能量填充2]]\n" +
                "@A $G1 %G0:你的<<DIAGRAM>>的等级1的分身1只作为对象，将其返回分身牌组。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SANGA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
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
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isLRIGTeam(CardLRIGTeam.DIAGRAM) && isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withColor(CardColor.BLUE).getValidTargetsCount() > 0 &&
               new TargetFilter().own().SIGNI().withColor(CardColor.BLACK).getValidTargetsCount() > 0 &&
               new TargetFilter().own().SIGNI().withColor(CardColor.GREEN).getValidTargetsCount() > 0)
            {
                enerCharge(1);
                
                CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
                addToHand(target);
            }
        }
        
        private void onEnterEff()
        {
            enerCharge(2);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TOP).own().anyLRIG().withLevel(1).withLRIGTeam(CardLRIGTeam.DIAGRAM)).get();
            returnToDeck(target, DeckPosition.TOP);
        }
    }
}
