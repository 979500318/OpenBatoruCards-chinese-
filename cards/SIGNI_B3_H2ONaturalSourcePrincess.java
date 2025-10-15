package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.actions.ActionTrash;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_B3_H2ONaturalSourcePrincess extends Card {
    
    public SIGNI_B3_H2ONaturalSourcePrincess()
    {
        setImageSets("WXDi-P05-038", "SPDi10-15", "WX25-P1-118");
        
        setOriginalName("羅原姫　Ｈ２Ｏ");
        setAltNames("ラゲンヒメエイチツーオー Ragenhime Eichi Tsuu Oo");
        setDescription("jp",
                "=R あなたの＜原子＞のシグニ１体の上に置く\n\n" +
                "@C：対戦相手のターンの間、下にカードが１枚以上あるこのシグニが対戦相手の効果によって場を離れる場合、代わりにこのシグニの下からすべてのカードをトラッシュに置いてもよい。\n" +
                "@U：このシグニがアタックしたとき、カードを１枚引くか、対戦相手は手札を１枚捨てる。" +
                "~#：カードを２枚引く。あなたの手札から＜原子＞のシグニを１枚まで場に出す。"
        );
        
        setName("en", "H2O, Natural Element Queen");
        setDescription("en",
                "=R Place on top of an <<Atom>> SIGNI on your field.\n\n" +
                "@C: During your opponent's turn, if this SIGNI has one or more cards underneath it and would leave the field by your opponent's effects, instead you may put all cards underneath this SIGNI into their owner's trash.\n" +
                "@U: Whenever this SIGNI attacks, draw a card or your opponent discards a card." +
                "~#Draw two cards. Put up to one <<Atom>> SIGNI from your hand onto your field."
        );
        
        setName("en_fan", "H2O, Natural Source Princess");
        setDescription("en_fan",
                "=R Put on 1 of your <<Atom>> SIGNI\n\n" +
                "@C: During your opponent's turn, if this SIGNI with 1 or more cards under it would leave the field due to your opponent's effect, you may put all cards under this SIGNI into the trash instead.\n" +
                "@U: Whenever this SIGNI attacks, draw 1 card or your opponent discards 1 card from their hand." +
                "~#Draw 2 cards. Put up to 1 <<Atom>> SIGNI from your hand onto the field."
        );
        
		setName("zh_simplified", "罗原姬 H20");
        setDescription("zh_simplified", 
                "=R在你的<<原子>>精灵1只的上面放置\n" +
                "@C $TP :下面的牌在1张以上的这只精灵因为对战对手的效果离场的场合，作为替代，可以从这只精灵的下面把全部的牌放置到废弃区。\n" +
                "@U :当这只精灵攻击时，抽1张牌或，对战对手把手牌1张舍弃。" +
                "~#抽2张牌。从你的手牌把<<原子>>精灵1张最多出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(3);
        setPower(13000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            setUseCondition(UseCondition.RISE, 1, new TargetFilter().withClass(CardSIGNIClass.ATOM));
            
            registerConstantAbility(this::onConstEffCond, new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.MOVE, OverrideScope.CALLER,OverrideFlag.NON_MANDATORY, this::onConstEffModOverrideCond,this::onConstEffModOverrideHandler))
            );
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return event.getSourceAbility() != null && !isOwnCard(event.getSourceCardIndex()) && !CardLocation.isSIGNI(((EventMove)event).getMoveLocation()) &&
                   getCardsUnderCount(CardUnderCategory.UNDER) > 0;
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            DataTable<CardIndex> data = new TargetFilter().under(getCardIndex()).getExportedData();
            ActionTrash prevAction = null;
            for(int i=0;i<data.size();i++)
            {
                ActionTrash action = new ActionTrash(data.get(i));
                action.setAtOnce(prevAction, i, data.size());
                prevAction = action;
                
                list.addAction(action);
            }
        }
        
        private void onAutoEff()
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.DISCARD) == 1)
            {
                draw(1);
            } else {
                discard(getOpponent(), 1);
            }
        }
        
        private void onLifeBurstEff()
        {
            draw(2);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.ATOM).fromHand().playable()).get();
            putOnField(cardIndex);
        }
    }
}
