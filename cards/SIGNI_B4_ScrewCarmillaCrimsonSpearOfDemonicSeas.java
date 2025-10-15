package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B4_ScrewCarmillaCrimsonSpearOfDemonicSeas extends Card {

    public SIGNI_B4_ScrewCarmillaCrimsonSpearOfDemonicSeas()
    {
        setImageSets("WDK02-011");

        setOriginalName("魔海の紅槍　スクリュー・カーミラ");
        setAltNames("マカイノコウソウスクリューカーミラ Makai no Kousou Sukuryuu Kaamira");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの手札が１枚以下の場合、対戦相手は手札を１枚捨てる。" +
                "~#：対戦相手のシグニ１体を対象とし、対戦相手の手札が２枚以下の場合、それをバニッシュする。"
        );

        setName("en", "Screw Carmilla, Crimson Spear of Demonic Seas");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there are 1 or less cards in your hand, your opponent discards 1 card from their hand." +
                "~#Target 1 of your opponent's SIGNI, and if there are 2 or less cards in your opponent's hand, banish it."
        );

		setName("zh_simplified", "魔海的红枪 螺旋·卡米拉");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的手牌在1张以下的场合，对战对手把手牌1张舍弃。" +
                "~#对战对手的精灵1只作为对象，对战对手的手牌在2张以下的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(getHandCount(getOwner()) <= 1)
            {
                discard(getOpponent(), 1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            if(target != null && getHandCount(getOpponent()) <= 2) banish(target);
        }
    }
}
