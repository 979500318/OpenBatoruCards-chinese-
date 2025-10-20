package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityGuard;

public final class SIGNI_W2_KokkuriSanPhantomApparition extends Card {

    public SIGNI_W2_KokkuriSanPhantomApparition()
    {
        setImageSets("WXDi-P10-049");

        setOriginalName("幻怪　コックリサン");
        setAltNames("ゲンカイコックリサン Genkai Kokkurisan");
        setDescription("jp",
                "@A @[このシグニを場からトラッシュに置く]@：あなたの白のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは@>@U：このシグニがバニッシュされたとき、このシグニをエナゾーンからダウン状態で場に出す。@@を得る。" +
                "~#：カードを１枚引く。このターン、あなたの手札にあるシグニは#Gを得る。"
        );

        setName("en", "Kokkuri-san, Phantom Spirit");
        setDescription("en",
                "@A @[Put this SIGNI on your field into its owner's trash]@: Target white SIGNI on your field gains@>@U: When this SIGNI is vanished, put it from your Ener Zone onto your field downed.@@until the end of your opponent's next end phase." +
                "~#Draw a card. The SIGNI in your hand gain a #G this turn. "
        );
        
        setName("en_fan", "Kokkuri-san, Phantom Apparition");
        setDescription("en_fan",
                "@A @[Put this SIGNI from the field into the trash]@: Target 1 of your white SIGNI, and until the end of your opponent's next turn, it gains:" +
                "@>@U: When this SIGNI is banished, put this SIGNI from the ener zone onto the field downed.@@" +
                "~#Draw 1 card. This turn, all SIGNI in your hand gain #G @[Guard]@."
        );

		setName("zh_simplified", "幻怪 狐狗狸");
        setDescription("zh_simplified", 
                "@A 这只精灵从场上放置到废弃区:你的白色的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其得到\n" +
                "@>@U :当这只精灵被破坏时，这张精灵从能量区以横置状态出场。@@" +
                "~#抽1张牌。这个回合，你的手牌的精灵得到#G。（持有#G的精灵得到[[防御]]）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
        setLevel(2);
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

            registerActionAbility(new TrashCost(), this::onActionEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.WHITE)).get();
            
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.BANISH, this::onAttachedAutoEff);
                attachAbility(target, attachedAuto, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        private void onAttachedAutoEff()
        {
            if(getAbility().getSourceCardIndex().getLocation() == CardLocation.ENER)
            {
                getAbility().getSourceCardIndex().getIndexedInstance().putOnField(getAbility().getSourceCardIndex(), Enter.DOWNED);
            }
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
