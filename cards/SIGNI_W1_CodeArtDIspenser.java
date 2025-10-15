package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class SIGNI_W1_CodeArtDIspenser extends Card {
    
    public SIGNI_W1_CodeArtDIspenser()
    {
        setImageSets("WXDi-P07-053");
        
        setOriginalName("コードアート　Dスペンサー");
        setAltNames("コードアートディースペンサー Koodo Aato Dii Supensa");
        setDescription("jp",
                "@E：あなたの手札からスペルを１枚公開してもよい。そうした場合、次の対戦相手のターン終了時まで、このシグニのパワーを＋5000する。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。カードを１枚引く。"
        );
        
        setName("en", "D - Spenser, Code: Art");
        setDescription("en",
                "@E: You may reveal a spell from your hand. If you do, this SIGNI gets +5000 power until the end of your opponent's next end phase." +
                "~#Target SIGNI on your opponent's field gains@>@C@#: This SIGNI cannot attack.@@@@until end of turn. Draw a card."
        );
        
        setName("en_fan", "Code Art D Ispenser");
        setDescription("en_fan",
                "@E: You may reveal 1 spell from your hand. If you do, until the end of your opponent's next turn, this SIGNI gets +5000 power." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C@#: Can't attack.@@@@" +
                "Draw 1 card."
        );
        
		setName("zh_simplified", "必杀代号 喷雾器");
        setDescription("zh_simplified", 
                "@E :可以从你的手牌把魔法1张公开。这样做的场合，直到下一个对战对手的回合结束时为止，这只精灵的力量+5000。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.REVEAL).own().spell().fromHand()).get();
            
            if(cardIndex != null && reveal(cardIndex))
            {
                addToHand(cardIndex);
                
                gainPower(getCardIndex(), 5000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            
            draw(1);
        }
    }
}
