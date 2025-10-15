package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.EnterAbility;

public final class LRIGA_K2_ChinatsuHinomiyaHotSprings extends Card {

    public LRIGA_K2_ChinatsuHinomiyaHotSprings()
    {
        setImageSets("WXDi-CP02-050");

        setOriginalName("火宮チナツ(温泉)");
        setAltNames("ヒノミヤチナツオンセン Hinomiya Chinatsu Onsen");
        setDescription("jp",
                "@E %X：ターン終了時まで、このルリグは@>@U $T1：あなたのシグニ１体が対戦相手のライフクロス１枚をクラッシュしたとき、そのシグニをアップし、ターン終了時まで、そのシグニは能力を失う。@@を得る。" +
                "~{{E：あなたのトラッシュから＜ブルアカ＞のカード１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Hinomiya Chinatsu (Hot Spring)");
        setDescription("en",
                "@E %X: This LRIG gains@>@U $T1: When a SIGNI on your field crushes one of your opponent's Life Cloth, up it and it loses its abilities until end of turn.@@until end of turn.~{{E: Add target <<Blue Archive>> card from your trash to your hand."
        );
        
        setName("en_fan", "Chinatsu Hinomiya (Hot Springs)");
        setDescription("en_fan",
                "@E: Until end of turn, this LRIG gains:" +
                "@>@U $T1: When 1 of your SIGNI crushes 1 of your opponent's life cloth, up that SIGNI, and until end of turn, it loses its abilities.@@" +
                "~{{E: Target 1 <<Blue Archive>> card from your trash, and add it to your hand."
        );

		setName("zh_simplified", "火宫千夏(温泉)");
        setDescription("zh_simplified", 
                "@E %X:直到回合结束时为止，这只分身得到\n" +
                "@>@U $T1 :当你的精灵1只把对战对手的生命护甲1张击溃时，那只精灵竖直，直到回合结束时为止，那只精灵的能力失去。@@\n" +
                "~{{E:从你的废弃区把<<ブルアカ>>牌1张作为对象，将其加入手牌。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.PREFECT_TEAM);
        setColor(CardColor.BLACK);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);

            EnterAbility enter2 = registerEnterAbility(this::onEnterEff2);
            enter2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff1()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.CRUSH, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachedAuto.setUseLimit(UseLimit.TURN, 1);
            
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && isOwnCard(getEvent().getSourceCardIndex()) && CardType.isSIGNI(getEvent().getSourceCardIndex().getIndexedInstance().getTypeByRef()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            up(getEvent().getSourceCardIndex());
            
            disableAllAbilities(getEvent().getSourceCardIndex(), AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }

        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromTrash()).get();
            addToHand(target);
        }
    }
}

