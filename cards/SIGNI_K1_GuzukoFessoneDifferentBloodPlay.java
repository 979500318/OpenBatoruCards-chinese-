package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_K1_GuzukoFessoneDifferentBloodPlay extends Card {

    public SIGNI_K1_GuzukoFessoneDifferentBloodPlay()
    {
        setImageSets("WXDi-P16-082");

        setOriginalName("異血之遊　グズ子//フェゾーネ");
        setAltNames("イチノユウグズコフェゾーネ Ichi no Yuu Guzuko Fezoone");
        setDescription("jp",
                "@U：このシグニが対戦相手のライフクロス１枚をクラッシュしたとき、対戦相手のシグニ１体を対象とし、このシグニを場からトラッシュに置いてもよい。そうした場合、ターン終了時まで、それのパワーを－5000する。\n\n" +
                "@A #C #C：このカードをトラッシュから場に出す。"
        );

        setName("en", "Guzuko//Fesonne, Strange Blood Play");
        setDescription("en",
                "@U: Whenever this SIGNI crushes one of your opponent's Life Cloth, you may put this SIGNI on your field into its owner's trash. If you do, target SIGNI on your opponent's field gets --5000 power until end of turn.\n\n@A #C #C: Put this card from your trash onto your field. "
        );
        
        setName("en_fan", "Guzuko//Fessone, Different Blood Play");
        setDescription("en_fan",
                "@U: Whenever this SIGNI crushes 1 of your opponent's life cloth, target 1 of your opponent's SIGNI, and you may put this SIGNI from the field into the trash. If you do, until end of turn, it gets --5000 power.\n\n" +
                "@A #C #C: Put this card from your trash onto the field."
        );

		setName("zh_simplified", "异血之游 迟钝子//音乐节");
        setDescription("zh_simplified", 
                "@U :当这只精灵把对战对手的生命护甲1张击溃时，对战对手的精灵1只作为对象，可以把这只精灵从场上放置到废弃区。这样做的场合，直到回合结束时为止，其的力量-5000。\n" +
                "@A #C #C:这张牌从废弃区出场。（这个能力只有在这张牌在废弃区的场合才能使用）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(1);
        setPower(3000);

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
            
            ActionAbility act = registerActionAbility(new CoinCost(2), this::onActionEff);
            act.setCondition(this::onActionEffCond);
            act.setActiveLocation(CardLocation.TRASH);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && getCardIndex().isSIGNIOnField() && playerChoiceActivate() && trash(getCardIndex()))
            {
                gainPower(target, -5000, ChronoDuration.turnEnd());
            }
        }

        private ConditionState onActionEffCond()
        {
            return isPlayable() ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff()
        {
            putOnField(getCardIndex());
        }
    }
}
