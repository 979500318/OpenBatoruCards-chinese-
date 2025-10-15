package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G1_OrobasVerdantDevil extends Card {
    
    public SIGNI_G1_OrobasVerdantDevil()
    {
        setImageSets("WXDi-P06-069");
        
        setOriginalName("翠魔　オロバス");
        setAltNames("スイマオロバス Suima Orobasu");
        setDescription("jp",
                "@U：このシグニがバニッシュされたとき、あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋3000する。\n" +
                "@E：あなたのエナゾーンからカード１枚を選びトラッシュに置く。"
        );
        
        setName("en", "Orobas, Jade Evil");
        setDescription("en",
                "@U: When this SIGNI is vanished, target SIGNI on your field gets +3000 power until end of turn.\n" +
                "@E: Choose a card from your Ener Zone and put it into your trash."
        );
        
        setName("en_fan", "Orobas, Verdant Devil");
        setDescription("en_fan",
                "@U: When this SIGNI is banished, target 1 of your SIGNI, and until end of turn, it gets +3000 power.\n" +
                "@E: Choose 1 card from your ener zone, and put it into the trash."
        );
        
		setName("zh_simplified", "翠魔 欧洛巴士");
        setDescription("zh_simplified", 
                "@U :当这只精灵被破坏时，你的精灵1只作为对象，直到回合结束时为止，其的力量+3000。\n" +
                "@E :从你的能量区选1张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            gainPower(target, 3000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BURN).own().fromEner()).get();
            trash(cardIndex);
        }
    }
}
