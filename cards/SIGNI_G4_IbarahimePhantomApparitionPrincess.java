package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_G4_IbarahimePhantomApparitionPrincess extends Card {

    public SIGNI_G4_IbarahimePhantomApparitionPrincess()
    {
        setImageSets("WXK01-042");
        setLinkedImageSets("WXK01_TK-01A");

        setOriginalName("幻怪姫　イバラヒメ");
        setAltNames("ゲンカイキイバラヒメ Genkaiki Ibarahime");
        setDescription("jp",
                "@U：あなたのターンにあなたがアーツを使用したとき、対戦相手のシグニ１体を対象とし、それがこのターンにあなたが使用した２枚目のアーツだった場合、それをエナゾーンに置く。\n" +
                "@E：あなたのルリグデッキに《棘々迷路》１枚を加える。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のパワー8000以上のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "Ibarahime, Phantom Apparition Princess");
        setDescription("en",
                "@U: During your turn, when you use ARTS, if it was the second ARTS you used this turn, target 1 of your opponent's SIGNI, and put it into the ener zone.\n" +
                "@E: Add 1 \"Thorny Maze\" ARTS craft into your LRIG deck." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 8000 or more, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "幻怪姬 荆棘妖姬");
        setDescription("zh_simplified", 
                "@U :当你的回合你把必杀使用时，对战对手的精灵1只作为对象，其是这个回合你使用的第2张的必杀的场合，将其放置到能量区。\n" +
                "@E :你的分身牌组加入《棘々迷路》1张。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的力量8000以上的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setLevel(4);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.USE_ARTS, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller())) == 2)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).OP().SIGNI()).get();
                putInEner(target);
            }
        }
        
        private void onEnterEff()
        {
            returnToDeck(craft(getLinkedImageSets().get(0)), DeckPosition.TOP);
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(8000,0)).get();
                banish(target);
            } else {
                enerCharge(1);
            }
        }
    }
}
