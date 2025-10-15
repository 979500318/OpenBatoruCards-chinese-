package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.modifiers.AbilityCopyModifier;
import open.batoru.data.ability.modifiers.CardNameModifier;

public final class LRIG_B4_AkiraLovely extends Card {

    public LRIG_B4_AkiraLovely()
    {
        setImageSets("WX24-P4-018", "WX24-P4-018U");

        setOriginalName("あきら☆らぶりー");
        setAltNames("アキララブリー Akira Raburii");
        setDescription("jp",
                "@C：このルリグはあなたのルリグトラッシュにあるレベル３の＜あきら＞と同じカード名としても扱い、そのルリグの@U能力を得る。\n" +
                "@E @[エクシード４]@：対戦相手のシグニ１体を対象とし、対戦相手が手札を３枚捨てないかぎり、それをバニッシュする。\n" +
                "@A $G1 @[@|ぶっとばす！|@]@ %B0：&E４枚以上@0カードを３枚引く。このターンのアタックフェイズの間、あなたのシグニ１体が場を離れたとき、カードを１枚引くか、対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Akira☆Lovely");
        setDescription("en",
                "@C: This LRIG is also treated as having the same card name as a level 3 <<Akira>> in your LRIG trash, and gains that LRIG's @U abilities.\n" +
                "@E @[Exceed 4]@: Target 1 of your opponent's SIGNI, and banish it unless your opponent discards 3 cards from their hand.\n" +
                "@A $G1 @[@|I'll Crush You!|@]@ %B0: &E4 or more@0 Draw 3 cards. During the attack phase of this turn, whenever 1 of your SIGNI leaves the field, draw 1 card or your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "晶☆可爱");
        setDescription("zh_simplified", 
                "@C 这只分身也视作与你的分身废弃区的等级3的<<あきら>>相同牌名，得到那张分身的@U能力。\n" +
                "@E @[超越 4]@:对战对手的精灵1只作为对象，如果对战对手不把手牌3张舍弃，那么将其破坏。\n" +
                "@A $G1 :猛打狠揍！%B0&E4张以上@0抽3张牌。这个回合的攻击阶段期间，当你的精灵1只离场时，抽1张牌或，对战对手把手牌1张舍弃。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AKIRA);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(4);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }


    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            TargetFilter filter = new TargetFilter().own().LRIG().withLRIGType(CardLRIGType.AKIRA).withLevel(3).fromTrash(DeckType.LRIG);
            registerConstantAbility(new CardNameModifier(filter), new AbilityCopyModifier(filter, ability -> ability instanceof AutoAbility));

            registerEnterAbility(new ExceedCost(4), this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("I'll Crush You!");
            act.setRecollect(4);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && discard(getOpponent(), 0,3, ChoiceLogic.BOOLEAN).size() != 3)
            {
                banish(target);
            }
        }

        private void onActionEff()
        {
            if(getAbility().isRecollectFulfilled())
            {
                draw(3);
                
                AutoAbility attachedAuto = new AutoAbility(GameEventId.MOVE, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return GamePhase.isAttackPhase(getCurrentPhase()) && isOwnCard(caller) && caller.isSIGNIOnField() &&
                   !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.DISCARD) == 1)
            {
                draw(1);
            } else {
                discard(getOpponent(), 1);
            }
        }
    }
}
