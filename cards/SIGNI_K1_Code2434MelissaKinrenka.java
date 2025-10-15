package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K1_Code2434MelissaKinrenka extends Card {
    
    public SIGNI_K1_Code2434MelissaKinrenka()
    {
        setImageSets("WXDi-P00-075");
        
        setOriginalName("コード２４３４　メリッサ・キンレンカ");
        setAltNames("コードニジサンジメリッサキンレンカ Koodo Nijisanji Merissa Kinrenka");
        setDescription("jp",
                "@C：あなたのトラッシュに＜バーチャル＞のシグニが５枚以上あるかぎり、このシグニのパワーは＋５０００される。\n" +
                "@E：あなたのデッキの上からカードを３枚トラッシュに置く。" +
                "~#：対戦相手のシグニ１体を対象とし、%K %Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－１２０００する。"
        );
        
        setName("en", "Melissa Kinrenka, Code 2434");
        setDescription("en",
                "@C: As long as there are five or more <<Virtual>> SIGNI in your trash, this SIGNI gets +5000 power.\n" +
                "@E: Put the top three cards of your deck into the trash." +
                "~#You may pay %K %X. If you do, target SIGNI on your opponent's field gets --12000 power until end of turn."
        );
        
        setName("en_fan", "Code 2434 Melissa Kinrenka");
        setDescription("en_fan",
                "@C: As long as there are 5 or more <<Virtual>> SIGNI in your trash, this SIGNI gets +5000 power.\n" +
                "@E: Put the top 3 cards of your deck into the trash." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %K %X. If you do, until end of turn, that SIGNI gets --12000 power."
        );
        
		setName("zh_simplified", "2434代号 梅丽莎·金莲花");
        setDescription("zh_simplified", 
                "@C :你的废弃区的<<バーチャル>>精灵在5张以上时，这只精灵的力量+5000。\n" +
                "@E :从你的牌组上面把3张牌放置到废弃区。" +
                "~#对战对手的精灵1只作为对象，可以支付%K%X。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().fromTrash().withClass(CardSIGNIClass.VIRTUAL).getValidTargetsCount() >= 5 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            millDeck(3);
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
