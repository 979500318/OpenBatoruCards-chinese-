package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
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
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.events.EventAttach;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_R3_ExThree extends Card {
    
    public LRIG_R3_ExThree()
    {
        setImageSets("WXDi-D07-004", Mask.IGNORE+"WXDi-P112");
        
        setOriginalName("エクス・スリー");
        setAltNames("エクススリー Ekusu Surii");
        setDescription("jp",
                "@U：あなたのシグニ１体に[[ソウル]]が付いたとき、手札を１枚捨ててもよい。そうした場合、カードを１枚引く。\n" +
                "@A %R0：あなたのシグニ１体を対象とし、このルリグの下からカード１枚をそれの[[ソウル]]にする。\n" +
                "@A $G1 @[エクシード３]@：あなたの赤のシグニ２体までを対象とし、あなたのルリグデッキにあるピース１枚をゲームから除外する。そうした場合、ターン終了時まで、それらは[[アサシン]]を得る。"
        );
        
        setName("en", "Ex Three");
        setDescription("en",
                "@U: Whenever a [[Soul]] is attached to a SIGNI on your field, you may discard a card. If you do, draw a card.\n" +
                "@A %R0: Attach a card underneath this LRIG to target SIGNI on your field as a [[Soul]].\n" +
                "@A $G1 @[Exceed 3]@: Remove a PIECE in your LRIG Deck from the game. If you do, up to two target red SIGNI on your field gain [[Assassin]] until end of turn."
        );
        
        setName("en_fan", "Ex Three");
        setDescription("en_fan",
                "@U: Whenever a [[Soul]] is attached to 1 of your SIGNI, you may discard 1 card from your hand. If you do, draw 1 card.\n" +
                "@A %R0: Target 1 of your SIGNI, and attach 1 card under this LRIG to it as a [[Soul]].\n" +
                "@A $G1 @[Exceed 3]@: Target up to 2 of your red SIGNI, and exclude 1 piece in your LRIG deck from the game. If you do, until end of turn, they gain [[Assassin]]."
        );
        
		setName("zh_simplified", "艾克斯·叁");
        setDescription("zh_simplified", 
                "@U :当你的精灵1只被[[灵魂]]附加时，可以把手牌1张舍弃。这样做的场合，抽1张牌。\n" +
                "@A %R0:你的精灵1只作为对象，从这只分身的下面把1张牌作为其的[[灵魂]]。\n" +
                "@A $G1 @[超越 3]@:你的红色的精灵2只最多作为对象，你的分身牌组的和音1张从游戏除外。这样做的场合，直到回合结束时为止，这些得到[[暗杀]]。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.EX);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.ATTACH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff1);
            act1.setCondition(this::onActionEff1Cond);
            
            ActionAbility act2 = registerActionAbility(new ExceedCost(3), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return EventAttach.getDataAttachUnderType() == CardUnderType.ATTACHED_SOUL &&
                   isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(discard(0,1).get() != null)
            {
                draw(1);
            }
        }
        
        private ConditionState onActionEff1Cond()
        {
            return new TargetFilter().own().under(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ATTACH).own().SIGNI().attachable(CardUnderType.ATTACHED_SOUL)).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.ATTACH).own().under(getCardIndex())).get();
                
                attach(target, cardIndex, CardUnderType.ATTACHED_SOUL);
            }
        }
        
        private void onActionEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.RED));
            
            if(data.get() != null)
            {
                CardIndex cardIndex = searchDeck(new TargetFilter(TargetHint.EXCLUDE).own().piece(), DeckType.LRIG).get();
                
                if(cardIndex != null && reveal(cardIndex) && exclude(cardIndex))
                {
                    for(int i=0;i<data.size();i++) attachAbility(data.get(i), new StockAbilityAssassin(), ChronoDuration.turnEnd());
                }
            }
        }
    }
}
