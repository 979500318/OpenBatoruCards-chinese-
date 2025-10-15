package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K3_Code2434RenaYorumi extends Card {
    
    public SIGNI_K3_Code2434RenaYorumi()
    {
        setImageSets("WXDi-P00-078");
        setLinkedImageSets("WXDi-P00-074");
        
        setOriginalName("コード２４３４　夜見れな");
        setAltNames("コードニジサンジヨルミレナ Koodo Nijisanji Yorumi Rena");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、ターン終了時まで、対戦相手のすべてのシグニのパワーをあなたの場にある＜バーチャル＞のシグニ１体につき－1000する。\n" +
                "@E：あなたの場に《コード２４３４　葉加瀬冬雪》がある場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－4000する。" +
                "~#：あなたのトラッシュから＜バーチャル＞のシグニ２枚までを対象とし、それらを手札に加える。"
        );
        
        setName("en", "Rena Yorumi, Code 2434");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, until end of turn, all SIGNI on your opponent's field gets --1000 power for each <<Virtual>> SIGNI on your field.\n" +
                "@E: If \"Code 2434 Fuyuki Hakase\" is on your field, target SIGNI on your opponent's field gets --4000 power until end of turn." +
                "~#Add up to two target <<Virtual>> SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Code 2434 Rena Yorumi");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, until end of turn, all of your opponent's SIGNI get --1000 power for each <<Virtual>> SIGNI on your field.\n" +
                "@E: If \"Code 2434 Fuyuki Hakase\" is on your field, target 1 of your opponent's SIGNI, and until end of turn, it gets --4000 power." +
                "~#Target up to 2 <<Virtual>> SIGNI from your trash, and add them to your hand."
        );
        
		setName("zh_simplified", "2434代号 夜见蕾娜");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，直到回合结束时为止，对战对手的全部的精灵的力量依据你的场上的<<バーチャル>>精灵的数量，每有1只就-1000。\n" +
                "@E :你的场上有《コード２４３４　葉加瀬冬雪》的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-4000。" +
                "~#从你的废弃区把<<バーチャル>>精灵2张最多作为对象，将这些加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            gainPower(getSIGNIOnField(getOpponent()), -1000 * new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).getValidTargetsCount(), ChronoDuration.turnEnd());
        }
        
        private void onEnterEff()
        {
            if(new TargetFilter().own().SIGNI().withName("コード２４３４　葉加瀬冬雪").getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -4000, ChronoDuration.turnEnd());
            }
        }
        
        private void onLifeBurstEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromTrash());
            addToHand(data);
        }
    }
}
