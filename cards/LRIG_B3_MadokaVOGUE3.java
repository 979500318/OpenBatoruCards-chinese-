package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_B3_MadokaVOGUE3 extends Card {
    
    public LRIG_B3_MadokaVOGUE3()
    {
        setImageSets("WXDi-P02-030", "SPDi07-11","SPDi08-11");
        
        setOriginalName("VOGUE3 マドカ");
        setAltNames("ボーグスリーマドカ Boogu Surii Madoka");
        setDescription("jp",
                "=T ＜DIAGRAM＞\n" +
                "^U $T1：あなたの効果によって対戦相手が手札を１枚捨てたとき、カードを１枚引くか[[エナチャージ１]]をする。\n" +
                "@E：カードを３枚引き、手札を２枚捨てる。\n" +
                "@A $G1 %B0：あなたの＜DIAGRAM＞のレベル１のルリグ１体を対象とし、それをルリグデッキに戻す。"
        );
        
        setName("en", "Madoka, Vogue 3");
        setDescription("en",
                "=T <<DIAGRAM>>\n" +
                "^U $T1: When your opponent discards a card by your effect, draw a card or [[Ener Charge 1]].\n" +
                "@E: Draw three cards and discard two cards.\n" +
                "@A $G1 %B0: Return target level one <<DIAGRAM>> LRIG on your field to your LRIG Deck."
        );
        
        setName("en_fan", "Madoka, VOGUE 3");
        setDescription("en_fan",
                "=T <<DIAGRAM>>\n" +
                "^U $T1: When your opponent discards 1 card from their hand due to your effect, draw 1 card or [[Ener Charge 1]].\n" +
                "@E: Draw 3 cards, and discard 2 cards from your hand.\n" +
                "@A $G1 %B0: Target 1 of your level 1 <<DIAGRAM>> LRIGs, and return it to the LRIG deck."
        );
        
		setName("zh_simplified", "VOGUE3 円");
        setDescription("zh_simplified", 
                "=T<<DIAGRAM>>\n" +
                "^U$T1 :当因为你的效果把对战对手的手牌1张舍弃时，抽1张牌或[[能量填充1]]。\n" +
                "@E :抽3张牌，手牌2张舍弃。\n" +
                "@A $G1 %B0:你的<<DIAGRAM>>的等级1的分身1只作为对象，将其返回分身牌组。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MADOKA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isLRIGTeam(CardLRIGTeam.DIAGRAM) && !isOwnCard(caller) &&
                   getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSourceCardIndex()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.ENER) == 1)
            {
                draw(1);
            } else {
                enerCharge(1);
            }
        }
        
        private void onEnterEff()
        {
            draw(3);
            discard(2);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TOP).own().anyLRIG().withLevel(1).withLRIGTeam(CardLRIGTeam.DIAGRAM)).get();
            returnToDeck(target, DeckPosition.TOP);
        }
    }
}
