package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B3_SharkSpearBlackSpearOfDemonicSeas extends Card {

    public SIGNI_B3_SharkSpearBlackSpearOfDemonicSeas()
    {
        setImageSets("WDK02-014");

        setOriginalName("魔海の黒槍　シャークスピア");
        setAltNames("マカイノコクソウシャークスピア Makai no Kokusou Shaaku Supia");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、あなたの手札が２枚以下の場合、ターン終了時まで、それのパワーを－1000する。あなたの手札が０枚の場合、代わりにターン終了時まで、それのパワーを－5000する。" +
                "~#：カードを１枚引く。"
        );

        setName("en", "Shark Spear, Black Spear of Demonic Seas");
        setDescription("en",
                "@E: Target 1 of your opponent's SIGNI, and if there are 2 or less cards in your hand, until end of turn, it gets --1000 power. If there are 0 cards in your hand, instead, until end of turn, it gets --5000 power." +
                "~#Draw 1 card."
        );

		setName("zh_simplified", "魔海的黑枪 夏克丝比");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，你的手牌在2张以下的场合，直到回合结束时为止，其的力量-1000。你的手牌在0张的场合，作为替代，直到回合结束时为止，其的力量-5000。" +
                "~#抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(7000);

        setPlayFormat(PlayFormat.KEY);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null && getHandCount(getOwner()) <= 2) gainPower(target, getHandCount(getOwner()) > 0 ? -1000 : -5000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            draw(1);
        }
    }
}
