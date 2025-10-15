package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K2_Code2434MorinakaKazaki extends Card {
    
    public SIGNI_K2_Code2434MorinakaKazaki()
    {
        setImageSets("WXDi-D02-22");
        
        setOriginalName("コード２４３４　森中花咲");
        setAltNames("コードニジサンジモリナカカザキ Koodo Nijisanji Morinaka Kazaki");
        setDescription("jp",
                "@E %K %W：あなたのトラッシュから《コード２４３４　御伽原江良》１枚を対象とし、それを場に出す。\n" +
                "~#：対戦相手のシグニ１体を対象とし、%K %Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );
        
        setName("en", "Kazaki Morinaka, Code 2434");
        setDescription("en",
                "@E %K %W: Put target \"Era Otogibara, Code 2434\" from your trash onto your field." +
                "~#You may pay %K %X. If you do, target SIGNI on your opponent's field gets --12000 power until end of turn."
        );
        
        setName("en_fan", "Code 2434 Morinaka Kazaki");
        setDescription("en_fan",
                "@E %K %W: Target 1 \"Code 2434 Era Otogibara\" from your trash, and put it onto the field." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %K %X. If you do, until end of turn, that SIGNI gets --12000 power."
        );
        
		setName("zh_simplified", "2434代号 森中花咲");
        setDescription("zh_simplified", 
                "@E %K%W:从你的废弃区把《コード２４３４　御伽原江良》1张作为对象，将其出场。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）" +
                "~#对战对手的精灵1只作为对象，可以支付%K%X。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.color(CardColor.WHITE, 1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withName("コード２４３４　御伽原江良").playable().fromTrash()).get();
            putOnField(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(cardIndex != null && payEner(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)))
            {
                gainPower(cardIndex, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
