package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class SIGNI_K2_HumptyDumptyMediumTrap extends Card {

    public SIGNI_K2_HumptyDumptyMediumTrap()
    {
        setImageSets("PR-K018", "WXK07-104");

        setOriginalName("中罠　ハンプティ・ダンプティ");
        setAltNames("チュウビンハンプティダンプティ Chuubin Hanputi Danputi");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、あなたの場にあるすべてのシグニのレベルが偶数の場合、ターン終了時まで、それのパワーを－4000する。" +
                "~#：カードを１枚引く。"
        );

        setName("en", "Humpty Dumpty, Medium Trap");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and if the levels of all SIGNI on your field are even, until end of turn, it gets -4000 power." +
                "~#Draw 1 card."
        );

		setName("zh_simplified", "中罠 鸡蛋·胖胖");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手的精灵1只作为对象，你的场上的全部的精灵的等级在偶数的场合，直到回合结束时为止，其的力量-4000。" +
                "~#抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClass.TRICK);
        setLevel(2);
        setPower(4000);

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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                DataTable<CardIndex> data = getSIGNIOnField(getOwner());
                for(int i=0;i<data.size();i++) if(data.get(i).getIndexedInstance().getLevel().getValue() % 2 != 0) return;
                
                gainPower(target, -4000, ChronoDuration.turnEnd());
            }
        }
        
        private void onLifeBurstEff()
        {
            draw(1);
        }
    }
}

