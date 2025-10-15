package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class SIGNI_K1_StolasWickedDevil extends Card {

    public SIGNI_K1_StolasWickedDevil()
    {
        setImageSets("WX24-P3-088");

        setOriginalName("凶魔　ストラス");
        setAltNames("キョウマストラス Kyouma Sutorasu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのデッキの上からカードを３枚トラッシュに置いてもよい。この方法で＜悪魔＞のシグニが１枚以上トラッシュに置かれた場合、対戦相手のデッキの上からカードを３枚トラッシュに置く。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Stolas, Wicked Devil");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may put the top 3 cards of your deck into the trash. If 1 or more <<Devil>> SIGNI were put into the trash this way, put the top 3 cards of your opponent's deck into the trash." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "凶魔 斯托拉斯");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，可以从你的牌组上面把3张牌放置到废弃区。这个方法把<<悪魔>>精灵1张以上放置到废弃区的场合，从对战对手的牌组上面把3张牌放置到废弃区。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            if(playerChoiceActivate())
            {
                DataTable<CardIndex> data = millDeck(3);
                
                if(data.get() != null && data.stream().anyMatch(c -> c.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.DEVIL)))
                {
                    millDeck(getOpponent(), 3);
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
