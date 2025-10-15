package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K1_CodeAntiMonkeysPawDissona extends Card {

    public SIGNI_K1_CodeAntiMonkeysPawDissona()
    {
        setImageSets("WXDi-P12-085");

        setOriginalName("コードアンチ　サルノテ//ディソナ");
        setAltNames("コードアンチサルノテディソナ Koodo Anchi Saru no Te Disona");
        setDescription("jp",
                "@C：あなたのトラッシュに#Sのカードが３枚以上あるかぎり、このシグニのパワーは＋5000される。\n" +
                "@E：あなたのデッキの上からカードを３枚トラッシュに置く。" +
                "~#：対戦相手のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );

        setName("en", "Monkey's Paw//Dissona, Code: Anti");
        setDescription("en",
                "@C: As long as there are three or more #S cards in your trash, this SIGNI gets +5000 power.\n@E: Put the top three cards of your deck into your trash." +
                "~#You may discard a card. If you do, target SIGNI on your opponent's field gets --12000 power until end of turn."
        );
        
        setName("en_fan", "Code Anti Monkey's Paw//Dissona");
        setDescription("en_fan",
                "@C: As long as there are 3 or more #S @[Dissona]@ cards in your trash, this SIGNI gets +5000 power.\n" +
                "@E: Put the top 3 cards of your deck into the trash." +
                "~#Target 1 of your opponent's SIGNI, and you may discard 1 card from your hand. If you do, until end of turn, it gets --12000 power."
        );

		setName("zh_simplified", "古兵代号 猴爪//失调");
        setDescription("zh_simplified", 
                "@C 你的废弃区的#S的牌在3张以上时，这只精灵的力量+5000。\n" +
                "@E :从你的牌组上面把3张牌放置到废弃区。" +
                "~#对战对手的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
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

            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().dissona().fromTrash().getValidTargetsCount() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            millDeck(3);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
