package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class RESONA_G3_AthleHARDGreenThirdPlayPrincess extends Card {

    public RESONA_G3_AthleHARDGreenThirdPlayPrincess()
    {
        setImageSets("WXDi-P11-TK04");

        setOriginalName("緑参ノ遊姫　アスレ【ＨＡＲＤ】");
        setAltNames("リョクサンノユウキアスレハード Ryokusan no Yuuki Asure Haado");
        setDescription("jp",
                "手札とエナゾーンからシグニを合計３枚トラッシュに置く\n\n" +
                "@U $T1：あなたのシグニがバトルによって対戦相手のシグニ１体をバニッシュしたとき、【エナチャージ１】をする。\n" +
                "@U：このシグニがバトルによって対戦相手のシグニ１体をバニッシュしたとき、このシグニをアップする。"
        );

        setName("en", "Asure [Hard], Green Extreme Play");
        setDescription("en",
                "Put three SIGNI from your hand and/or Ener Zone into your trash.\n\n" +
                "@U $T1: When a SIGNI on your field vanishes a SIGNI on your opponent's field through battle, [[Ener Charge 1]].\n" +
                "@U: Whenever this SIGNI vanishes a SIGNI on your opponent's field through battle, up this SIGNI."
        );
        
        setName("en_fan", "Athle [HARD], Green Third Play Princess");
        setDescription("en_fan",
                "Put 3 SIGNI from your hand and/or ener zone into the trash\n\n" +
                "@U $T1: When your SIGNI banishes an opponent's SIGNI in battle, [[Ener Charge 1]].\n" +
                "@U: When this SIGNI banishes an opponent's SIGNI in battle, up this SIGNI."
        );
        
		setName("zh_simplified", "绿叁之游姬 木绳攀爬拓展【HARD】");
        setDescription("zh_simplified", 
                "[[出现条件]]主要阶段从手牌和能量区把精灵合计3张放置到废弃区\n" +
                "@U $T1 :当你的精灵因为战斗把对战对手的精灵1只破坏时，[[能量填充1]]。\n" +
                "@U :当这只精灵因为战斗把对战对手的精灵1只破坏时，这只精灵竖直。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.RESONA);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(3);
        setPower(12000);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            setUseCondition(UseCondition.RESONA, 3, new TargetFilter().or(new TargetFilter().fromHand(), new TargetFilter().fromEner()));

            AutoAbility auto1 = registerAutoAbility(GameEventId.BANISH, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.BANISH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceAbility() == null && isOwnCard(getEvent().getSourceCardIndex()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            enerCharge(1);
        }

        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceAbility() == null && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            up();
        }
    }
}

