package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;

public final class SIGNI_K2_BluebeardWickedDevil extends Card {

    public SIGNI_K2_BluebeardWickedDevil()
    {
        setImageSets("WX24-P3-090");

        setOriginalName("凶魔　アオヒゲ");
        setAltNames("キョウマアオヒゲ Kyouma Aohige");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのデッキの上からカードを３枚トラッシュに置いてもよい。その後、この方法で＜悪魔＞のシグニが１枚以上トラッシュに置かれた場合、対戦相手のシグニ１体を対象とし、%Kを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Bluebeard, Wicked Devil");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may put the top 3 cards of your deck into the trash. Then, if 1 or more <<Devil>> SIGNI were put into the trash this way, target 1 of your opponent's SIGNI, and you may pay %K. If you do, until end of turn, it gets --5000 power."
        );

		setName("zh_simplified", "凶魔 蓝胡子");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，可以从你的牌组上面把3张牌放置到废弃区。然后，这个方法把<<悪魔>>精灵1张以上放置到废弃区的场合，对战对手的精灵1只作为对象，可以支付%K。这样做的场合，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(5000);

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
        }
        
        private void onAutoEff()
        {
            if(playerChoiceActivate())
            {
                DataTable<CardIndex> data = millDeck(3);
                
                if(data.get() != null && data.stream().anyMatch(c -> c.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.DEVIL)))
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                    
                    if(target != null && payEner(Cost.color(CardColor.BLACK, 1)))
                    {
                        gainPower(target, -5000, ChronoDuration.turnEnd());
                    }
                }
            }
        }
    }
}
