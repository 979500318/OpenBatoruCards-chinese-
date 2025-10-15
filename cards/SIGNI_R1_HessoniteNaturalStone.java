package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventTarget;

public final class SIGNI_R1_HessoniteNaturalStone extends Card {

    public SIGNI_R1_HessoniteNaturalStone()
    {
        setImageSets("WXDi-P11-058", "SPDi01-99");

        setOriginalName("羅石　ヘソナイト");
        setAltNames("ラセキヘソナイト Raseki Hesonaito");
        setDescription("jp",
                "@U $T1：対戦相手のターンの間、このシグニが対戦相手の、能力か効果の対象になったとき、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。"
        );

        setName("en", "Hessonite, Natural Crystal");
        setDescription("en",
                "@U $T1: During your opponent's turn, when this SIGNI becomes the target of an ability or effect of your opponent, your opponent chooses a card from their Ener Zone and puts it into their trash."
        );
        
        setName("en_fan", "Hessonite, Natural Stone");
        setDescription("en_fan",
                "@U $T1: During your opponent's turn, when this SIGNI is targeted by your opponent's ability or effect, your opponent chooses 1 card from their ener zone, and puts it into the trash."
        );

		setName("zh_simplified", "罗石 钙铝榴石");
        setDescription("zh_simplified", 
                "@U $TP $T1 :当这只精灵被作为对战对手的，能力或效果的对象时，对战对手从自己的能量区选1张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.TARGET, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() && getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) &&
                    CardLocation.isSIGNI(getCardIndex().getLocation()) &&
                    EventTarget.getDataSourceTargetRole() != getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
            trash(cardIndex);
        }
    }
}
