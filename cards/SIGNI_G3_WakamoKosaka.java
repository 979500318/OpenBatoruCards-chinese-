package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_G3_WakamoKosaka extends Card {

    public SIGNI_G3_WakamoKosaka()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_LEFT+"WXDi-CP02-059");

        setOriginalName("狐坂ワカモ");
        setAltNames("コサカワカモ Kosaka Wakamo");
        setDescription("jp",
                "@U $T1：このシグニが対戦相手のライフクロス１枚をクラッシュしたとき、あなたのエナゾーンから＜ブルアカ＞のカード３枚をトラッシュに置いてもよい。そうした場合、このターンの、次のルリグアタックステップ開始時、対戦相手のライフクロス１枚をクラッシュする。" +
                "~{{A @[アップ状態のルリグ２体をダウンする]@：【エナチャージ１】@@" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "Kosaka Wakamo");
        setDescription("en",
                "@U $T1: When this SIGNI crushes one of your opponent's Life Cloth, you may put three <<Blue Archive>> cards from your Ener Zone into your trash. If you do, at the beginning of the next LRIG Attack Step this turn, crush one of your opponent's Life Cloth.~{{A @[Down two upped LRIG]@: [[Ener Charge 1]].@@" +
                "~#Choose one -- \n$$1Vanish target upped SIGNI on your opponent's field. \n$$2[[Ener Charge 1]]."
        );
        
        setName("en_fan", "Wakamo Kosaka");
        setDescription("en_fan",
                "@U $T1: When this SIGNI crushes 1 of your opponent's life cloth, you may put 3 <<Blue Archive>> cards from your ener zone into the trash. If you do, at the beginning of your next LRIG attack step of this turn, crush 1 of your opponent's life cloth." +
                "~{{A @[Down 2 of your upped LRIG]@: [[Ener Charge 1]]@@" +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "狐坂若藻");
        setDescription("zh_simplified", 
                "@U $T1 :当这只精灵把对战对手的生命护甲1张击溃时，可以从你的能量区把<<ブルアカ>>牌3张放置到废弃区。这样做的场合，这个回合的，下一个分身攻击步骤开始时，对战对手的生命护甲1张击溃。\n" +
                "~{{A竖直状态的分身2只#D:[[能量填充1]]@@" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.CRUSH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act = registerActionAbility(new DownCost(2, new TargetFilter().anyLRIG()), this::onActionEff);
            act.getFlags().addValue(AbilityFlag.BONDED);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private int cacheTurn;
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner());
            
            if(trash(data) == 3)
            {
                cacheTurn = getTurnCount();
                callDelayedEffect(ChronoDuration.nextPhase(GamePhase.ATTACK_LRIG), () -> {
                    if(getTurnCount() == cacheTurn) crush(getOpponent());
                });
            }
        }

        private void onActionEff()
        {
            enerCharge(1);
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                enerCharge(1);
            }
        }
    }
}
