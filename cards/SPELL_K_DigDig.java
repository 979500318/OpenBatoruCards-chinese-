package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class SPELL_K_DigDig extends Card {

    public SPELL_K_DigDig()
    {
        setImageSets("WXDi-P13-088");

        setOriginalName("ディグ・ディグ");
        setAltNames("ディグディグ Digu Digu");
        setDescription("jp",
                "あなたのデッキの上からカードを３枚トラッシュに置いてもよい。その後、あなたのトラッシュから#Sのシグニ１枚を対象とし、それを手札に加える。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。"
        );

        setName("en", "Dig - Dig");
        setDescription("en",
                "You may put the top three cards of your deck into your trash. Then, add target #S SIGNI from your trash to your hand." +
                "~#Target upped SIGNI on your opponent's field gets --15000 power until end of turn."
        );
        
        setName("en_fan", "Dig Dig");
        setDescription("en_fan",
                "You may put the top 3 cards of your deck into the trash. Then, target 1 #S @[Dissona]@ SIGNI from your trash, and add it to your hand." +
                "~#Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power."
        );

		setName("zh_simplified", "摇荡·调和");
        setDescription("zh_simplified", 
                "可以从你的牌组上面把3张牌放置到废弃区。然后，从你的废弃区把#S的精灵1张作为对象，将其加入手牌。" +
                "~#对战对手的竖直状态的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerSpellAbility(this::onSpellEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEff()
        {
            if(playerChoiceActivate())
            {
                millDeck(3);
            }
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().dissona().fromTrash()).get();
            addToHand(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().upped()).get();
            gainPower(target, -15000, ChronoDuration.turnEnd());
        }
    }
}
