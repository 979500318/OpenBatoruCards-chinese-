package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_B3_DrTamagoGuts extends Card {
    
    public LRIG_B3_DrTamagoGuts()
    {
        setImageSets("WXDi-D05-004", "SPDi07-07","SPDi08-07");
        
        setOriginalName("ガッツ　Ｄｒ．タマゴ");
        setAltNames("ガッツドクタータマゴ Gattsu Dokutaa Tamago");
        setDescription("jp",
                "=T ＜うちゅうのはじまり＞\n" +
                "^E：カードを１枚引く。対戦相手は手札を１枚捨てる。\n" +
                "@U：このルリグがアタックしたとき、このルリグの下にカードが５枚以上ある場合、カードを１枚引く。７枚以上ある場合、追加で対戦相手は手札を１枚捨てる。\n" +
                "@A $G1 %B0：あなたの他のルリグの下にあるすべてのカードをこのルリグの下に置く。"
        );
        
        setName("en", "Never Give Up! Dr. Tamago");
        setDescription("en",
                "=T <<UCHU NO HAJIMARI>>\n" +
                "^E: Draw a card. Your opponent discards a card.\n" +
                "@U: Whenever this LRIG attacks, if there are five or more cards underneath it, draw a card. If there are seven or more cards underneath it, your opponent also discards a card.\n" +
                "@A $G1 %B0: Put all cards underneath all other LRIG on your field under this LRIG."
        );
        
        setName("en_fan", "Dr. Tamago, Guts");
        setDescription("en_fan",
                "=T <<Universe's Beginning>>\n" +
                "^E: Draw 1 card. Your opponent discards 1 card from their hand.\n" +
                "@U: Whenever this LRIG attacks, if there are 5 or more cards under this LRIG, draw 1 card. If there are 7 or more cards under this LRIG, additionally your opponent discards 1 card from their hand.\n" +
                "@A $G1 %B0: Put all of the cards under your other LRIGs under this LRIG."
        );
        
		setName("zh_simplified", "狼吞 Dr.玉子");
        setDescription("zh_simplified", 
                "=T<<うちゅうのはじまり>>\n" +
                "^E:抽1张牌。对战对手把手牌1张舍弃。\n" +
                "@U :当这只分身攻击时，这只分身的下面的牌在5张以上的场合，抽1张牌。7张以上的场合，追加对战对手把手牌1张舍弃。\n" +
                "@A $G1 %B0:你的其他的分身的下面的全部的牌放置到这只分身的下面。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMAGO);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
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
            
            EnterAbility enter = registerEnterAbility(this::onEnterEff);
            enter.setCondition(this::onEnterEffCond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onEnterEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onEnterEff()
        {
            draw(1);
            discard(getOpponent(), 1);
        }
        
        private void onAutoEff()
        {
            int countUnder = new TargetFilter().own().under(getLRIG(getOwner())).getValidTargetsCount();
            
            if(countUnder >= 5) draw(1);
            if(countUnder >= 7) discard(getOpponent(), 1);
        }
        
        private void onActionEff()
        {
            forEachLRIGOnField(getOwner(), cardIndex -> {
                if(cardIndex == getCardIndex()) return;
                
                forEachCardUnder(cardIndex, cardIndexUnder -> {
                    attach(getCardIndex(), cardIndexUnder, CardUnderType.UNDER_GENERIC);
                });
            });
        }
    }
}
