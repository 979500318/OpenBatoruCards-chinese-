package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class SIGNI_W1_Code2434SisterCleaire extends Card {
    
    public SIGNI_W1_Code2434SisterCleaire()
    {
        setImageSets("WXDi-P00-044");
        
        setOriginalName("コード２４３４　シスター・クレア");
        setAltNames("コードニジサンジシスタークレア Koodo Nijisanji Shisutaa Kurea");
        setDescription("jp",
                "@E：あなたの＜バーチャル＞のシグニ１体を対象とし、それをアップする。\n" +
                "@E：あなたの場に《コード２４３４　緑仙》と《コード２４３４　ドーラ》がある場合、カードを１枚引く。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。カードを１枚引く。"
        );
        
        setName("en", "Sister Claire, Code 2434");
        setDescription("en",
                "@E: Up target <<Virtual>> SIGNI on your field.\n" +
                "@E: If 《Ryushen, Code 2434》 and 《Dola, Code 2434》 are both on your field, draw a card." +
                "~#Target SIGNI on your opponent's field gains" +
                "@>@C@#: Can't Attack.@@@@until end of turn. Draw 1 card."
        );
        
        setName("en_fan", "Code 2434 Sister Claire");
        setDescription("en_fan",
                "@E: Target 1 of your <<Virtual>> SIGNI, and up it.\n" +
                "@E: If \"Code 2434 Ryushen\" and \"Code 2434 Dola\" are on your field, draw 1 card." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, that SIGNI gains:" +
                "@>@C@#: Can't Attack.@@@@" +
                "Draw 1 card."
        );
        
		setName("zh_simplified", "2434代号 修女·克蕾雅");
        setDescription("zh_simplified", 
                "@E :你的<<バーチャル>>精灵1只作为对象，将其竖直。\n" +
                "@E :你的场上有《コード２４３４　緑仙》和《コード２４３４　ドーラ》的场合，抽1张牌。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff1()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.UP).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL)).get();
            up(cardIndex);
        }
        
        private void onEnterEff2()
        {
            if(new TargetFilter().own().SIGNI().withName("コード２４３４　緑仙").getValidTargetsCount() > 0 &&
               new TargetFilter().own().SIGNI().withName("コード２４３４　ドーラ").getValidTargetsCount() > 0)
            {
                draw(1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            attachAbility(cardIndex, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            
            draw(1);
        }
    }
}
