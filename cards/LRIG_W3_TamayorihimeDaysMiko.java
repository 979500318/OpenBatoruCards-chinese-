package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_W3_TamayorihimeDaysMiko extends Card {

    public LRIG_W3_TamayorihimeDaysMiko()
    {
        setImageSets("WX24-P1-011", "WX24-P1-011U");

        setOriginalName("月日の巫女　タマヨリヒメ");
        setAltNames("ツキヒノミコタマヨリヒメ Tsukihi no Miko Tamayorihime");
        setDescription("jp",
                "@U：このルリグがアタックしたとき、あなたの場に＜アーム＞のシグニがある場合、手札を１枚捨て%Wを支払ってもよい。そうした場合、このルリグをアップし、ターン終了時まで、このルリグは能力を失う。\n" +
                "@A $G1 @[@|ホープ|@]@ %W0：このターン、次にあなたのシグニ１体がバトルによってシグニ１体をバニッシュしたとき、そのあなたのシグニをアップし、ターン終了時まで、そのシグニは能力を失う。"
        );

        setName("en", "Tamayorihime, Days Miko");
        setDescription("en",
                "@U: Whenever this LRIG attacks, if there is an <<Arm>> SIGNI on your field, you may discard 1 card from your hand, and pay %W. If you do, up this LRIG, and until end of turn, it loses its abilities.\n" +
                "@A @[@|Hope|@]@ $G1 %W0: The next time one of your SIGNI banishes a SIGNI by battle, up that SIGNI, and until end of turn, it loses its abilities."
        );

		setName("zh_simplified", "月日的巫女 玉依姬");
        setDescription("zh_simplified", 
                "@U :当这只分身攻击时，你的场上有<<アーム>>精灵的场合，可以把手牌1张舍弃并支付%W。这样做的场合，这只分身竖直，直到回合结束时为止，这只分身的能力失去。\n" +
                "@A $G1 希望%W0:这个回合，当下一次你的精灵1只因为战斗把精灵1只破坏时，那只你的精灵竖直，直到回合结束时为止，那只精灵的能力失去。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMA);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        setLevel(3);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Hope");
        }

        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ARM).getValidTargetsCount() > 0 &&
               payAll(new DiscardCost(1), new EnerCost(Cost.color(CardColor.WHITE, 1))))
            {
                up();
                disableAllAbilities(getCardIndex(), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            }
        }
        
        private void onActionEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.BANISH, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceAbility() == null && !isOwnCard(caller) && isOwnCard(getEvent().getSourceCardIndex()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex cardIndexSource = getEvent().getSourceCardIndex();
            up(cardIndexSource);
            disableAllAbilities(cardIndexSource, AbilityGain.ALLOW, ChronoDuration.turnEnd());
            
            CardAbilities.removePlayerAbility(getAbility());
        }
    }
}
