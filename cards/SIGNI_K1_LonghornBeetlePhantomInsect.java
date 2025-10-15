package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_LonghornBeetlePhantomInsect extends Card {
    
    public SIGNI_K1_LonghornBeetlePhantomInsect()
    {
        setImageSets("WXDi-P04-081");
        
        setOriginalName("幻蟲　カミキリムシ");
        setAltNames("ゲンチュウカミキリムシ Genchuu Kamikirimushi");
        setDescription("jp",
                "@E：あなたの場に、シグニに付いているカードかシグニの下に置かれているカードがある場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－1000する。"
        );
        
        setName("en", "Longhorn Beetle, Phantom Insect");
        setDescription("en",
                "@E: If there is a card underneath or attached to a SIGNI on your field, target SIGNI on your opponent's field gets --1000 power until end of turn."
        );
        
        setName("en_fan", "Longhorn Beetle, Phantom Insect");
        setDescription("en_fan",
                "@E: If there is a card attached to a SIGNI or a card placed under a SIGNI on your field, target 1 of your opponent's SIGNI, and until end of turn, it gets --1000 power."
        );
        
		setName("zh_simplified", "幻虫 天牛");
        setDescription("zh_simplified", 
                "@E :你的场上有，精灵附加的牌或精灵的下面放置的牌的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-1000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            if(new TargetFilter().own().SIGNI().withUnderType(CardUnderCategory.ATTACHED.getFlags() | CardUnderCategory.UNDER.getFlags()).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -1000, ChronoDuration.turnEnd());
            }
        }
    }
}
