package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.*;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_K1_CodeRideMachinaMemoria extends Card {

    public SIGNI_K1_CodeRideMachinaMemoria()
    {
        setImageSets("WXDi-P11-077", "WXDi-P11-077P");

        setOriginalName("コードライド　マキナ//メモリア");
        setAltNames("コードライドマキナメモリア Koodo Raido Makina Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このシグニの下からカードを好きな枚数トラッシュに置く。この方法でトラッシュに置いたカード１枚につき対戦相手のデッキの上からカードを１枚トラッシュに置く。\n" +
                "@E：あなたのデッキの一番上のカードをこのシグニの下に置く。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Machina//Memoria, Code: Ride");
        setDescription("en",
                "@U: At the beginning of your attack phase, put any number of cards underneath this SIGNI into their owner's trash. Put the top card of your opponent's deck into their trash for each card put into the trash this way.\n" +
                "@E: Put the top card of your deck under this SIGNI." +
                "~#Target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Code Ride Machina//Memoria");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, put any number of cards from under this SIGNI into the trash. For each card put into the trash this way, put the top card of your opponent's deck into the trash.\n" +
                "@E: Put the top card of your deck under this SIGNI." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "骑乘代号 玛琪娜//回忆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从这只精灵的下面把牌任意张数放置到废弃区。依据这个方法放置到废弃区的牌的数量，每有1张就从对战对手的牌组上面把1张牌放置到废弃区。\n" +
                "@E :你的牌组最上面的牌放置到这只精灵的下面。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.RIDING_MACHINE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.TRASH).own().SIGNI().under(getCardIndex()));
            millDeck(getOpponent(), trash(data));
        }
        
        private void onEnterEff()
        {
            attach(getCardIndex(), CardLocation.DECK_MAIN, CardUnderType.UNDER_GENERIC);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
