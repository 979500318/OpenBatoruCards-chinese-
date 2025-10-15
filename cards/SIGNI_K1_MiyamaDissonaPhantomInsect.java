package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public class SIGNI_K1_MiyamaDissonaPhantomInsect extends Card {

    public SIGNI_K1_MiyamaDissonaPhantomInsect()
    {
        setImageSets("WXDi-P13-085");

        setOriginalName("幻蟲　ミヤマ//ディソナ");
        setAltNames("ゲンチュウミヤマディソナ Genchuu Miyama Disona");
        setDescription("jp",
                "@U $T1：あなたの#Sのカードの効果によって対戦相手のデッキからカードが１枚以上トラッシュに置かれたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。"
        );

        setName("en", "Miyama//Dissona, Phantom Insect");
        setDescription("en",
                "@U $T1: When one or more cards are put into your opponent's trash from their deck by your #S card's effect, target SIGNI on your opponent's field gets --2000 power until end of turn."
        );
        
        setName("en_fan", "Miyama//Dissona, Phantom Insect");
        setDescription("en_fan",
                "@U $T1: When 1 or more cards are put from your opponent's deck into the trash by the effect of your #S @[Dissona]@ card, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );

		setName("zh_simplified", "幻虫 深山锹形虫//失调");
        setDescription("zh_simplified", 
                "@U $T1 当因为你的#S的牌的效果从对战对手的牌组把牌1张以上放置到废弃区时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
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

            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getEvent().isAtOnce(1) && caller.isEffectivelyAtLocation(CardLocation.DECK_MAIN) &&
                   getEvent().getSourceAbility() != null && getEvent().getSourceCost() == null && isOwnCard(getEvent().getSourceAbility().getSourceCardIndex()) &&
                   getEvent().getSourceAbility().getSourceCardIndex().getIndexedInstance().isState(CardStateFlag.IS_DISSONA) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
    }
}
