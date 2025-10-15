package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityGuard;

public final class SIGNI_W1_KamakamaSmallEquipment extends Card {

    public SIGNI_W1_KamakamaSmallEquipment()
    {
        setImageSets("WXDi-P16-061");

        setOriginalName("小装　カマカマ");
        setAltNames("ショウソウカマカマ Shousou Kamakama");
        setDescription("jp",
                "@A #D：次の対戦相手のターン終了時まで、このシグニのパワーを＋7000する。" +
                "~#：カードを１枚引く。このターン、あなたの手札にあるシグニは#Gを得る。"
        );

        setName("en", "Kamakama, Lightly Armed");
        setDescription("en",
                "@A #D: This SIGNI gets +7000 power until the end of your opponent's next end phase." +
                "~#Draw a card. The SIGNI in your hand gain a #G this turn. "
        );
        
        setName("en_fan", "Kamakama, Small Equipment");
        setDescription("en_fan",
                "@A #D: Until the end of your opponent's next turn, this SIGNI gets +7000 power." +
                "~#Draw 1 card. This turn, all SIGNI in your hand gain #G @[Guard]@."
        );

		setName("zh_simplified", "小装 镰镰");
        setDescription("zh_simplified", 
                "@A #D:直到下一个对战对手的回合结束时为止，这只精灵的力量+7000。" +
                "~#抽1张牌。这个回合，你的手牌的精灵得到#G。（持有#G的精灵得到[[防御]]）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerActionAbility(new DownCost(), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onActionEff()
        {
            gainPower(getCardIndex(), 7000, ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private void onLifeBurstEff()
        {
            draw(1);

            ConstantAbilityShared attachedConstShared = new ConstantAbilityShared(new TargetFilter().own().SIGNI().fromHand(), new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachPlayerAbility(getOwner(), attachedConstShared, ChronoDuration.turnEnd());
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityGuard());
        }
    }
}
