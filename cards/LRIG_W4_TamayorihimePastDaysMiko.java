package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityCopyModifier;
import open.batoru.data.ability.modifiers.CardNameModifier;

public final class LRIG_W4_TamayorihimePastDaysMiko extends Card {

    public LRIG_W4_TamayorihimePastDaysMiko()
    {
        setImageSets("WX24-P4-011", "WX24-P4-011U");

        setOriginalName("過日の巫女　タマヨリヒメ");
        setAltNames("カジツノミコタマヨリヒメ Kajitsu no Miko Tamayorihime");
        setDescription("jp",
                "@C：このルリグはあなたのルリグトラッシュにあるレベル３の＜タマ＞と同じカード名としても扱い、そのルリグの@U能力を得る。\n" +
                "@E @[エクシード４]@：このターン、次にこのルリグがアタックしたとき、このルリグをアップする。\n" +
                "@A $G1 @[@|ホープ|@]@ %W %X：&E４枚以上@0このターン、あなたのシグニがバトルによってシグニ１体をバニッシュしたとき、対戦相手が%X %Xを支払わないかぎり、対戦相手にダメージを与える。"
        );

        setName("en", "Tamayorihime, Past Days Miko");
        setDescription("en",
                "@C: This LRIG is also treated as having the same card name as a level 3 <<Tama>> in your LRIG trash, and gains that LRIG's @U abilities.\n" +
                "@E @[Exceed 4]@: This turn, the next time this LRIG attacks, up this LRIG.\n" +
                "@A $G1 @[@|Hope|@]@ %W %X: &E4 or more@0 This turn, whenever your SIGNI banishes an opponent's SIGNI by battle, damage your opponent unless they pay %X %X."
        );

		setName("zh_simplified", "过日的巫女 玉依姬");
        setDescription("zh_simplified", 
                "@C 这只分身也视作与你的分身废弃区的等级3的<<タマ>>相同牌名，得到那张分身的@U能力。\n" +
                "@E @[超越 4]@:这个回合，当下一次这只分身攻击时，这只分身竖直。\n" +
                "@A $G1 希望%W%X&E4张以上@0这个回合，当你的精灵因为战斗把精灵1只破坏时，如果对战对手不把%X %X:支付，那么给予对战对手伤害。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMA);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(4);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }


    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final EnterAbility enter;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            TargetFilter filter = new TargetFilter().own().LRIG().withLRIGType(CardLRIGType.TAMA).withLevel(3).fromTrash(DeckType.LRIG);
            registerConstantAbility(new CardNameModifier(filter), new AbilityCopyModifier(filter, ability -> ability instanceof AutoAbility));
            
            enter = registerEnterAbility(new ExceedCost(4), this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Hope");
            act.setRecollect(4);
        }

        private void onEnterEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff1);
            attachedAuto.setCondition(this::onAttachedAutoEff1Cond);
            attachPlayerAbility(getOwner(), enter,attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEff1Cond(CardIndex caller)
        {
            return caller == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff1(CardIndex caller)
        {
            up();
            CardAbilities.removePlayerAbility(getAbility());
        }

        private void onActionEff()
        {
            if(getAbility().isRecollectFulfilled())
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.BANISH, this::onAttachedAutoEff2);
                attachedAuto.setCondition(this::onAttachedAutoEff2Cond);
                attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedAutoEff2Cond(CardIndex caller)
        {
            return getEvent().getSourceAbility() == null && isOwnCard(getEvent().getSource()) && CardType.isSIGNI(getEvent().getSource().getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff2(CardIndex caller)
        {
            if(!payEner(getOpponent(), Cost.colorless(2)))
            {
                damage(getOpponent());
            }
        }
    }
}
